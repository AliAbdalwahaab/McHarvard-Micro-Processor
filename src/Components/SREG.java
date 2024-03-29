package Components;

public class SREG {
    private byte sreg = 0x00; //000CVNSZ

    //set Carry Flag (C)
    public void setCarryFlag(byte value) {
        value = (byte) (value << 4);
        sreg = (byte) (sreg | value);
    }

    //set Two's Complement Overflow Flag (V)
    public void setOverflowFlag(byte value) {
        value = (byte) (value << 3);
        sreg = (byte) (sreg | value);
    }

    //set Sign Flag (S)
    //the logic to get this value should be implemented in the ALU
    public void setSignFlag(byte value) {
        value = (byte) (value << 1);
        sreg = (byte) (sreg | value);
    }

    //set Negative Flag (N)
    public void setNegativeFlag(byte value) {
        value = (byte) (value << 2);
        sreg = (byte) (sreg | value);
    }

    //set Zero Flag (Z)
    public void setZeroFlag(byte value) {
        sreg = (byte) (sreg | value);
    }

    //get Carry Flag (C)
    public byte getCarryFlag() {
        byte valToReturn = sreg;
        return (byte) ((valToReturn >>> 4) & 0x01);
    }

    //get Two's Complement Overflow Flag (V)
    public byte getOverflowFlag() {
        byte valToReturn = sreg;
        valToReturn = (byte) (valToReturn >>> 3);
        return (byte) (valToReturn & 0x01);
    }

    //get Negative Flag (N)
    public byte getNegativeFlag() {
        byte valToReturn = sreg;
        valToReturn = (byte) (valToReturn >>> 2);
        return (byte) (valToReturn & 0x01);
    }

    //get Zero Flag (Z)
    public byte getZeroFlag() {
        byte valToReturn = sreg;
        return (byte) (valToReturn & 0x01);
    }

    //get Sign Flag (S)
    public byte getSignFlag() {
        byte valToReturn = sreg;
        valToReturn = (byte) (valToReturn >>> 1);
        return (byte) (valToReturn & 0x01);
    }

    public byte getAllFlags() {
        return sreg;
        //max value should be 31 (0b00011111) otherwise there is a problem
    }

    public void SetAllFlags(byte cFlag, byte vFlag, byte nFlag, byte sFlag, byte zFlag) {
        setCarryFlag(cFlag);
        setOverflowFlag(vFlag);
        setNegativeFlag(nFlag);
        setZeroFlag(zFlag);
        setSignFlag(sFlag);
    }

    public void nullifyRegister() {
        this.sreg = 0x00;
    }

    public String toString() {
        return "SREG: " + sreg + " (" + String.format("%8s", Integer.toBinaryString(sreg & 0xFF)).replace(' ', '0') + ")";
    }

    public String guiText() {
        return ""+ sreg + " (" + String.format("%8s", Integer.toBinaryString(sreg & 0xFF)).replace(' ', '0') + ")";
    }
}
