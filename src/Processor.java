import Components.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public Processor() {
        this.alu = new ALU();
        this.registerFile = new RegisterFile();
        this.pc = new PC();
        this.sreg = new SREG();
        this.dataMemory = new DataMemory();
        this.instructionMemory = new InstructionMemory();
        this.cycles = 1;
        this.lastInstruction = false;
    }

    public void fetch() {
        // fetch instruction from instruction memory
        short currAddress = pc.getAddress();

        if (currAddress > instructionMemory.getInstrCount() - 1) {
            PLNRegsBus.insertIntoPlnInstructions((short) -1);

        } else if (currAddress <= instructionMemory.getInstrCount() - 1) { // pipeline stall [ending cycles]
            short currInstruction = instructionMemory.getInstruction(currAddress);
            PLNRegsBus.insertIntoPlnInstructions(currInstruction);
            pc.increment();
        }
    }

    public void decode() {
        short currInstruction = PLNRegsBus.getDecodeInstruction();
        byte opcode = (byte) 0;
        byte operand1 = (byte) 0;
        byte operand2 = (byte) 0;

        if (currInstruction != -1) { // TODO pipeline stall [TBD starting and ending cycles]
            // decode instruction
            opcode = (byte) (currInstruction >>> 12 & 0xF);

            // Operand 1 is always a register
            operand1 = (byte) (currInstruction >>> 6 & 0x3F);
            registerFile.setReadReg1(operand1);
            operand1 = registerFile.getReadData1();

            // Operand 2 is either a register or an immediate value
            operand2 = (byte) (currInstruction & 0x3F);
            if ((opcode >= 0 && opcode <= 2) || (opcode >= 5 && opcode <= 7)) { // R-Type
                registerFile.setReadReg2(operand2);
                operand2 = registerFile.getReadData2();
            }

            // set operand1, operand2, opcode in ALU
            alu.setOpcode(opcode);
            alu.setOperands(operand1, operand2);

            // set operands and opcode in PLNRegsBus
            PLNRegsBus.setDecodeOperands(opcode, operand1, operand2);
        } else if (PLNRegsBus.getExecuteInstruction() != -1) {
            // shift to Exec stage
            PLNRegsBus.setDecodeOperands(opcode, operand1, operand2);
        }
    }

    public void execute() {
        short currInstruction = PLNRegsBus.getExecuteInstruction();

        if (currInstruction != -1) { // TODO pipeline stall [TBD starting cycles]
            // use operands and opcode to execute instruction
            byte[] ExecData = PLNRegsBus.getExecuteData();

            // get result from ALU and act accordingly
            alu.setOpcode(ExecData[0]);
            alu.setOperands(ExecData[1], ExecData[2]);
            short result = alu.getResult();

            // flush if opcode is a branch or jr
            if (ExecData[0] == 4 && result == 0x4F) {
                // flush fetch and decode
                PLNRegsBus.flushFetchDecode();
                // set PC to result
                pc.setAddress(alu.ALUAdder(pc.getAddress(), (byte) result));
            }

            else if (ExecData[0] == 7) {
                // flush fetch and decode
                PLNRegsBus.flushFetchDecode();
                // set PC to result
                pc.setAddress(result);
            }

            else {
                switch (ExecData[0]) {
                    case 0, 1, 2, 3, 5, 6, 8, 9: // ADD
                         registerFile.setRegWrite(true);
                         registerFile.setWriteData(ExecData[1], (byte) result);
                         registerFile.setRegWrite(false);
                        break;
                    case 10: // LB
                        dataMemory.getData((byte) result);
                        registerFile.setRegWrite(true);
                        registerFile.setWriteData(ExecData[1], (byte) result);
                        registerFile.setRegWrite(false);
                        break;
                    case 11: // SB
                        registerFile.setReadReg1(ExecData[1]);
                        dataMemory.setMemWrite(true);
                        dataMemory.setData((byte) result, (byte) registerFile.getReadData1());
                        dataMemory.setMemWrite(false);
                        break;
                }
            }

            //check for last instruction to insert -1 and terminate
            if (currInstruction == instructionMemory.getInstruction((short) (instructionMemory.getInstrCount() - 1))) {
                lastInstruction = true;
                PLNRegsBus.insertIntoPlnInstructions((short)-1);
            }
        }
    }

    public void executeProgram() {
        while (!lastInstruction) {
            System.out.println("Cycle: " + cycles);
            fetch();
            decode();
            execute();
            cycles++;
        }
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

        // operand 2
        byte opcode = (byte) (instruction >>> 12);

        if (opcode >= 8 && opcode <= 11 || opcode == 3 || opcode == 4) { // I-Type
            instruction = (short) (instruction | (Byte.parseByte(split[2]) & 0x3F));
        } else { // R-Type
            instruction = (short) (instruction | (Byte.parseByte(split[2].substring(1)) & 0x3F));
        }

        return instruction;

    }

    public void loadAssemblyAndParse() throws IOException {
        // load assembly code from text file
        BufferedReader br = new BufferedReader(new FileReader("src/assembly.txt"));
        while (br.ready()) {
            String line = br.readLine();
            short instruction = parseAssemblyLine(line);
            instructionMemory.addInstruction(instruction);
        }
    }
}
