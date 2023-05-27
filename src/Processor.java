import Components.*;

import java.io.*;
import java.util.Vector;

public class Processor {
    ALU alu;
    PLNRegsBus PLNRegsBus;
    RegisterFile registerFile;
    PC pc;
    SREG sreg;
    DataMemory dataMemory;
    InstructionMemory instructionMemory;
    int cycles;
    boolean lastInstruction;
    String log;

    //boolean branchJump;

    public Processor() {
        this.alu = new ALU();
        this.registerFile = new RegisterFile();
        this.pc = new PC();
        this.sreg = new SREG();
        this.dataMemory = new DataMemory();
        this.instructionMemory = new InstructionMemory();
        this.cycles = 1;
        this.lastInstruction = false;
        this.PLNRegsBus = new PLNRegsBus();
        //this.branchJump = false;
    }

    public void fetch() {
        // fetch instruction from instruction memory
        short currAddress = pc.getAddress();

        if (currAddress > instructionMemory.getInstrCount() - 1) {
            PLNRegsBus.insertIntoPlnInstructions((short) -1, (short) -1);

        } else if (currAddress <= instructionMemory.getInstrCount() - 1) { // pipeline stall [ending cycles]
            System.out.println("Fetching instruction at address " + currAddress + " from instruction memory.");
            log += "Fetching instruction at address " + currAddress + " from instruction memory.\n";
            short currInstruction = instructionMemory.getInstruction(currAddress);
            System.out.println("Instruction: " + currInstruction);
            log += "Instruction: " + currInstruction + "\n";
            PLNRegsBus.insertIntoPlnInstructions(currInstruction, currAddress);
            pc.increment();
        }
    }

    public void decode() {
        short currInstruction = PLNRegsBus.getDecodeInstruction();
        byte opcode = (byte) 0;
        byte operand1 = (byte) 0;
        byte operand2 = (byte) 0;

        if (currInstruction != -1) { // TODO pipeline stall [TBD starting and ending cycles]
            System.out.println("Decoding instruction " + currInstruction + ".");
            log += "Decoding instruction " + currInstruction + ".\n";
            // decode instruction
            opcode = (byte) (currInstruction >>> 12 & 0xF);

            // Operand 1 is always a register
            operand1 = (byte) (currInstruction >>> 6 & 0x3F);
            registerFile.setReadReg1(operand1);
            byte operand1RES = registerFile.getReadData1();

            // Operand 2 is either a register or an immediate value
            operand2 = (byte) (currInstruction & 0x3F);
            byte operand2RES = operand2;
            if ((opcode >= 0 && opcode <= 2) || (opcode >= 5 && opcode <= 7)) { // R-Type
                registerFile.setReadReg2(operand2);
                operand2RES = registerFile.getReadData2();
            } else {
                operand2 = from6to8(operand2);
                operand2RES = operand2;
            }

            // set operand1, operand2, opcode in ALU
            //alu.setOpcode(opcode);
            //alu.setOperands(operand1RES, operand2RES);

            // set operands and opcode in PLNRegsBus
            PLNRegsBus.setDecodeOperands(opcode, operand1RES, operand2RES, operand1, operand2);
        } else if (PLNRegsBus.getExecuteInstruction() != -1) {
            // shift to Exec stage
            PLNRegsBus.setDecodeOperands(opcode, (byte) -1, (byte) -1, (byte) 0, (byte)0);
        }
    }

    public void execute() {
        short currInstruction = PLNRegsBus.getExecuteInstruction();

        if (currInstruction != -1) { // TODO pipeline stall [TBD starting cycles]
            System.out.println("Executing instruction " + currInstruction + ".");
            log += "Executing instruction " + currInstruction + ".\n";



            // use operands and opcode to execute instruction
            short[] ExecData = PLNRegsBus.getExecuteData();

            System.out.println("Opcode: " + ExecData[0]);
            log += "Opcode: " + ExecData[0] + ".\n";
            System.out.println("Operand 1: " + ExecData[1]);
            log += "Operand 1: " + ExecData[1] + ".\n";
            System.out.println("Operand 2: " + ExecData[2]);
            log += "Operand 2: " + ExecData[2] + ".\n";

            short currPC = ExecData[5];
            // get result from ALU and act accordingly
            alu.setOpcode((byte)ExecData[0]);
            alu.setOperands((byte)ExecData[1], (byte)ExecData[2]);
            short result = alu.getResult();
            sreg = alu.sreg;

            // flush if opcode is a branch
            if (ExecData[0] == 4) {

                // set PC to result
                pc.setAddress(alu.ALUAdder(currPC, (byte) result));
                System.out.println("PC set to " + pc.getAddress() + ".");
                log += "PC set to " + pc.getAddress() + ".\n";

                // flush fetch and decode
                PLNRegsBus.flushDecodeAndFetch();
                //branchJump = true;
            }

            else if (ExecData[0] == 7) {
                // set PC to result
                pc.setAddress(result);
                System.out.println("PC set to " + pc.getAddress() + ".");
                log += "PC set to " + pc.getAddress() + ".\n";


                // flush fetch and decode
                PLNRegsBus.flushDecodeAndFetch();
                //branchJump = true;
            }

            else {
                switch (ExecData[0]) {
                    case 0, 1, 2, 3, 5, 6, 8, 9: // ADD
                         registerFile.setRegWrite(true);
                         registerFile.setWriteData(ExecData[3], (byte) result);
                         registerFile.setRegWrite(false);
                        System.out.println("R"+ExecData[3]+" value changed to " + result + ".");
                        log += "R"+ExecData[3]+" value changed to " + result + ".\n";
                        break;
                    case 10: // LB
                        result = dataMemory.getData((byte) result);
                        registerFile.setRegWrite(true);
                        registerFile.setWriteData(ExecData[3], (byte) result);
                        registerFile.setRegWrite(false);
                        System.out.println("R"+ExecData[3]+" value changed to " + result + ".");
                        log += "R"+ExecData[3]+" value changed to " + result + ".\n";
                        break;
                    case 11: // SB
                        registerFile.setReadReg1((byte)ExecData[3]);
                        dataMemory.setMemWrite(true);
                        dataMemory.setData((byte) result, (byte) registerFile.getReadData1());
                        dataMemory.setMemWrite(false);
                        System.out.println("Memory address " + result + " changed to " + registerFile.getReadData1() + ".");
                        log += "Memory address " + result + " changed to " + registerFile.getReadData1() + ".\n";
                        break;
                }
            }

            //check for last instruction to insert -1 and terminate
            if (currInstruction == instructionMemory.getInstruction((short) (instructionMemory.getInstrCount() - 1))) {
                lastInstruction = true;
                PLNRegsBus.insertIntoPlnInstructions((short) -1, (short) -1);
            }
        }
    }

    public void executeProgram() {
        while (!lastInstruction) {
            System.out.println("--------------------------------------------------");
            log += "--------------------------------------------------\n";
            System.out.println("Cycle: " + cycles);
            log += "Cycle: " + cycles + "\n";
            fetch();
            decode();
            execute();
            System.out.println(sreg);
            System.out.println("PC: " + pc.getAddress());
            log += "PC: " + pc.getAddress() + "\n";
            cycles++;
        }
        registerFile.printRegisters();
        dataMemory.printData();
        instructionMemory.printInstructions();
    }

    public void reset() {
        this.alu = new ALU();
        this.registerFile = new RegisterFile();
        this.pc = new PC();
        this.sreg = new SREG();
        this.dataMemory = new DataMemory();
        this.instructionMemory = new InstructionMemory();
        this.cycles = 1;
        this.lastInstruction = false;
        this.PLNRegsBus = new PLNRegsBus();
        this.log = "";
    }

    public void step() {
        if (!lastInstruction) {
            System.out.println("--------------------------------------------------");
            log += "--------------------------------------------------\n";
            System.out.println("Cycle: " + cycles);
            log += "Cycle: " + cycles + "\n";
            fetch();
            decode();
            execute();
            System.out.println(sreg);
            System.out.println("PC: " + pc.getAddress());
            log += "PC: " + pc.getAddress() + "\n";
            cycles++;
        }
        registerFile.printRegisters();
        dataMemory.printData();
        instructionMemory.printInstructions();
    }

//    public void flushFetchDecode() { // for branch and jr
//        op1 = false;
//        op2 = false;
//        opcode = -1;
//        operand1 = -1;
//        operand2 = -1;
//    }

    public short parseAssemblyLine(String assemblyLine) {

        String[] split = assemblyLine.split(" ");
        short instruction = 0;

        // opcode
        switch (split[0].toUpperCase()) {
            case "ADD": instruction = (short) (instruction | (0x0 << 12));break;
            case "SUB": instruction = (short) (instruction | (0x1 << 12));break;
            case "MUL": instruction = (short) (instruction | (0x2 << 12));break;
            case "LDI": instruction = (short) (instruction | (0x3 << 12));break;
            case "BEQZ": instruction = (short) (instruction | (0x4 << 12));break;
            case "AND": instruction = (short) (instruction | (0x5 << 12));break;
            case "OR": instruction = (short) (instruction | (0x6 << 12));break;
            case "JR": instruction = (short) (instruction | (0x7 << 12));break;
            case "SLC": instruction = (short) (instruction | (0x8 << 12));break;
            case "SRC":instruction = (short) (instruction | (0x9 << 12));break;
            case "LB":instruction = (short) (instruction | (0xA << 12));break;
            case "SB":instruction = (short) (instruction | (0xB << 12));break;
        }

        // operand 1
        instruction = (short) (instruction | ((Byte.parseByte(split[1].substring(1)) & 0x3F) << 6));


        byte opcode = (byte) ((instruction >>> 12) & 0xF);

        // operand 2
        if (opcode >= 8 && opcode <= 11 || opcode == 3 || opcode == 4) { // I-Type
            byte imm = (Byte.parseByte(split[2]));
            instruction = (short) (instruction | from8to6(imm));
        } else { // R-Type
            instruction = (short) (instruction | (Byte.parseByte(split[2].substring(1)) & 0x3F));
        }

        return instruction;

    }

    public void loadAssemblyAndParse() throws IOException {
        log = "";
        // load assembly code from text file
        BufferedReader br = new BufferedReader(new FileReader("src/assembly.txt"));
        while (br.ready()) {
            String line = br.readLine();
            short instruction = parseAssemblyLine(line);
            instructionMemory.addInstruction(instruction);
        }
    }

    public byte from8to6(byte b) {
        if (b < 0) {
            return (byte) (((b*-1) & 0x1F) | 0x20);
        } else {
            return (byte) (b & 0x3F);
        }
    }

    public byte from6to8(byte b) {
        if ((b & 0x20) == 0x20) {
            return (byte) ((byte)(b & 0x1F) * -1);
        } else {
            return b;
        }
    }

    public void detectAndFixHazards() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/assembly.txt"));
        Vector<String> lines = new Vector<String>();
        String line1 = "";
        if (br.ready()) {
            line1 = br.readLine();
        }
        String line2 = "";
        while (br.ready()) {
            line2 = br.readLine();
            lines.add(line1);
            String[] line1Split = line1.split(" ");
            String[] line2Split = line2.split(" ");
            if (line1Split[1].equals(line2Split[1]) || line1Split[1].equals(line2Split[2])) {
                lines.add("NOP");
            }
            line1 = line2;
        }

        if (line2.length() > 0) {
            lines.add(line2);
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("src/assembly.txt", false));
        for (String line : lines) {
            bw.write(line);
            if (!line.equals(lines.lastElement()))
                bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        Processor processor = new Processor();
        // processor.detectAndFixHazards();
        processor.loadAssemblyAndParse();
        processor.executeProgram();
        //processor.gui.setVisible(true);
    }
}
