package Components;

public class RegisterFile {

    //special purpose registers will either be in Main class or have a class of their own
    private byte[] registers = new byte[64];
    private boolean regWrite;
    private byte readReg1;
    private byte readReg2;

    public void setRegWrite(boolean regWrite) {
        this.regWrite = regWrite;
    }

    public void setReadReg1(byte readReg1) {
        this.readReg1 = readReg1;
    }

    public void setReadReg2(byte readReg2) {
        this.readReg2 = readReg2;
    }

    public byte getReadData1() {
        return registers[readReg1];
    }

    public byte getReadData2() {
        return registers[readReg2];
    }

    public void setWriteData(int writeReg, byte writeData) {
        if (regWrite) {
            registers[writeReg] = writeData;
        } else {
            System.out.println("Register File is not in write mode!");
        }
    }

    public void printRegisters() {
        System.out.println("=====================================");
        System.out.println("Register File Contents:");
        for (int i = 0; i < registers.length; i++) {
            System.out.println("R" + i + ": " +registers[i]);
        }
        System.out.println("=====================================");
    }
}