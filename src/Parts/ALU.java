package src.Parts;

public class ALU {
    public SREG sreg;
    private byte operand1;
    private byte operand2;
    private short result;
    private byte opcode;

    public ALU(byte operand1, byte operand2, byte opcode) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.opcode = opcode;
        sreg = new SREG();
    }

    public short getResult() {
        //TODO: implement the logic for the ALU
        return result;
    }

    //will be used to compute new value of pc in case of branch or jr
    public short ALUAdder(short pc, byte imm) {
        return 0;
    }


}
