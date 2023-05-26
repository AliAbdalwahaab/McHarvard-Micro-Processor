import Components.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Processor {
    ALU alu;
    RegisterFile registerFile;
    PC pc;
    SREG sreg;
    DataMemory dataMemory;
    InstructionMemory instructionMemory;
    int cycles;
    short currInstruction = -1;
    boolean lastInstruction = false;
    byte opcode;
    byte operand1;
    byte operand2;
    byte ALUresult;
    boolean op1;
    boolean op2;

    // TODO: Discuss BUS Component holds 3 diff versions of {instruction, decode_data, execute}

    public Processor() {
        this.alu = new ALU();
        this.registerFile = new RegisterFile();
        this.pc = new PC();
        this.sreg = new SREG();
        this.dataMemory = new DataMemory();
        this.instructionMemory = new InstructionMemory();
        this.cycles = 1;
        this.op1 = false;
        this.op2 = false;
        this.lastInstruction = false;
        this.currInstruction = -1;
    }

    public void fetch() {
        // fetch instruction from instruction memory
        short currAddress = pc.getAddress();
        if (currAddress == instructionMemory.getInstrCount() - 1) { // last instruction
            lastInstruction = true;
        }

        if (currAddress < instructionMemory.getInstrCount()) { // pipeline stall [ending cycles]
            currInstruction = instructionMemory.getInstruction(currAddress);
            pc.increment();
        }
    }

    public void decode() {
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

            // pipline stall markers [TBD starting cycles]
            op1 = true;
            op2 = true;
        }
    }

    public void execute() {
        if (op1) { // TODO pipeline stall [TBD starting cycles]
            // use operands and opcode to execute instruction

            // get result from ALU and act accordingly

            // flush if opcode is a branch or jr

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

    public void flushFetchDecode() { // for branch and jr
        currInstruction = -1;
        op1 = false;
        op2 = false;
        opcode = -1;
        operand1 = -1;
        operand2 = -1;
    }

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
