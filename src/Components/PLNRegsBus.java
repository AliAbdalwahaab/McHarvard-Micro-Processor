package Components;

public class PLNRegsBus {
    public static short[] plnInstructions = new short[3]; //[0] => Fetch Instruction, [1] => Decode Instruction, [2] => Execute Instruction
    public static byte[] plnOpCodes = new byte[2]; //[0] => Decode OpCode, [1] => Execute OpCode
    public static byte[] plnOp1 = new byte[2];
    public static byte[] plnOp2 = new byte[2];
    public static byte[] plnOp1Reg = new byte[2];
    public static byte[] plnOp2Reg = new byte[2];

    public PLNRegsBus(){
        plnInstructions = new short[] {-1,-1,-1};
        plnOpCodes = new byte[] {-1,-1};
        plnOp1 = new byte[] {-1,-1};
        plnOp2 = new byte[] {-1,-1};
    }
    //Set the plnInstructions array to the current instructions present in the instruction memory
    public void insertIntoPlnInstructions(short instruction) {
        if (plnInstructions[0] + plnInstructions[1] + plnInstructions[2] != -3){
            plnInstructions[2] = plnInstructions[1];
            plnInstructions[1] = plnInstructions[0];
            plnInstructions[0] = (short) (instruction & 0xFFFF);
        } else {
            plnInstructions[0] = (short) (instruction & 0xFFFF);
        }
    }

    public short getFetchInstruction(){
         return (short) (plnInstructions[0] & 0xFFFF);
    }

    public short getDecodeInstruction(){
        return (short) (plnInstructions[1] & 0xFFFF);
    }

    public short getExecuteInstruction(){
        return (short) (plnInstructions[2] & 0xFFFF);
    }

    public void setDecodeOperands (byte opCode, byte op1, byte op2, byte op1Reg, byte op2Reg){
        // check if the current values are not -1
        if (plnOpCodes[0] + plnOp1[0] + plnOp2[0] == -3){
            //Shift the current values to the right
             plnOpCodes[0] = opCode;
             plnOp1[0] = op1;
             plnOp2[0] = op2;
             plnOp1Reg[0] = op1Reg;
             plnOp2Reg[0] = op2Reg;
        } else {
            //Shift the current values to the right
            plnOpCodes[1] = plnOpCodes[0];
            plnOp1[1] = plnOp1[0];
            plnOp2[1] = plnOp2[0];
            plnOp1Reg[1] = plnOp1Reg[0];
            plnOp2Reg[1] = plnOp2Reg[0];

            //Set the new values of the next stage
            plnOpCodes[0] = opCode;
            plnOp1[0] = op1;
            plnOp2[0] = op2;
            plnOp1Reg[0] = op1Reg;
            plnOp2Reg[0] = op2Reg;
        }
    }

    public byte[] getExecuteData(){
        //return all the data needed for the current execute stage
        return new byte[] {plnOpCodes[1], plnOp1[1], plnOp2[1], plnOp1Reg[1], plnOp2Reg[1]};
    }

    public void flushFetchDecode(){
        plnInstructions[0] = -1;
        plnInstructions[1] = -1;
        plnOpCodes[0] = -1;
        plnOp1[0] = -1;
        plnOp2[0] = -1;
        plnOp1Reg[0] = -1;
        plnOp2Reg[0] = -1;
    }
}
