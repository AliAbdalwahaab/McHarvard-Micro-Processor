package Components;

public class RegisterFile {

    //special purpose registers will either be in Main class or have a class of their own
    private byte[] registers = new byte[64];
    private boolean regWrite;
    private int readReg1;
    private int readReg2;
}