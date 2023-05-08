package src.Parts;

public class InstructionMemory {

    private short[] instructions = new short[1024];
    private int address;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String toString() {
        String r = "Instruction Memory Contents:\n";
        for (int i = 0; i < instructions.length; i++) {
            r += "Address: "+i+", Value: "+instructions[i]+"\n";
        }
        return r;
    }
}
