package Components;

public class InstructionMemory {

    private short[] instructions = new short[1024];
    private short address;

    public short getAddress() {
        return address;
    }

    public void setAddress(short address) {
        this.address = address;
    }

    public short getInstruction(short address) {
        return instructions[address];
    }

    public void setInstruction(short instruction) {
        instructions[address] = instruction;
    }

    public String toString() {
        String r = "Instruction Memory Contents:\n";
        for (int i = 0; i < instructions.length; i++) {
            r += "Address: "+i+", Value: "+instructions[i]+"\n";
        }
        return r;
    }
}