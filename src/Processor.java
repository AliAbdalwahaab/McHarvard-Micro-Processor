import Components.*;

public class Processor {
    ALU alu;
    RegisterFile registerFile;
    PC pc;
    SREG sreg;
    DataMemory dataMemory;
    InstructionMemory instructionMemory;

    public Processor() {
        alu = new ALU();
        registerFile = new RegisterFile();
        pc = new PC();
        sreg = new SREG();
        dataMemory = new DataMemory();
        instructionMemory = new InstructionMemory();
    }

    public void fetch() {
        // fetch instruction from instruction memory
        // increment PC
    }

    public void decode() {
        // decode instruction
        // store operands in registers
    }

    public void execute() {
        // execute instruction
        // store result in register
    }

    public void parseAssembly(String assembly) {
        // parse assembly code
        // store instructions in instruction memory
    }
}
