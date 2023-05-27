package Components;

public class InstructionMemory {

    private short[] instructions = new short[1024];
    private short instrCount = 0;

    public short getInstruction(short address) {
        return instructions[address];
    }

    public void setInstruction(short instruction, short address) {
        instructions[address] = instruction;
    }

    public void addInstruction(short instruction) {
        if (instrCount < instructions.length) {
            instructions[instrCount++] = instruction;
        } else {
            System.out.println("Instruction Memory is full!");
        }
    }

    public short getInstrCount() {
        return instrCount;
    }

    public String toString() {
        String r = "";
        for (int i = 0; i < instructions.length; i++) {
            r += "Address: "+i+": "+instructions[i]+"\n";
        }
        return r;
    }

    public void printInstructions() {
        System.out.println("=====================================");
        System.out.println("Instruction Memory Contents:");
        for (int i = 0; i < instructions.length; i++) {
            System.out.println("Address " + i + ": " + instructions[i] + " (" + String.format("%16s", Integer.toBinaryString(instructions[i] & 0xFFFF)).replace(' ', '0') + ")");
        }
        System.out.println("=====================================");
    }
}