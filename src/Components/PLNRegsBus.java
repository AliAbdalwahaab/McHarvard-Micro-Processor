package Components;

public class PLNRegsBus {
    private static byte[] plnInstructions = new byte[3]; //[0] => Fetch Instruction, [1] => Decode Instruction, [2] => Execute Instruction
    private static byte[] plnOpCodes = new byte[2]; //[0] => Decode OpCode, [1] => Execute OpCode
    private static byte[] plnOp1 = new byte[2];
    private static byte[] plnOp2 = new byte[2];

    public PLNRegsBus(){
        plnInstructions = new byte[] {-1,-1,-1};
        plnOpCodes = new byte[] {-1,-1};
        plnOp1 = new byte[] {-1,-1};
        plnOp2 = new byte[] {-1,-1};
    }
    //Set the plnInstructions array to the current instructions present in the instruction memory
    public void insertIntoPlnInstructions(short instruction) {

        while (instruction != -1){
            if (plnInstructions[0] + plnInstructions[1] + plnInstructions[2] != -3){

                plnInstructions[2] = plnInstructions[1];
                plnInstructions[1] = plnInstructions[0];
                plnInstructions[0] = (byte) (instruction & 0xFF);
                instruction = (short) (instruction >> 8);
            }
            else{

                plnInstructions[0] = (byte) (instruction & 0xFF);
                instruction = (short) (instruction >> 8);
            }
        }
    }

    public byte getFetchInstruction(short[] instructions){
         return (byte) (instructions[0] & 0xFF);
    }

    public byte getDecodeInstruction(){
        return (byte) (plnInstructions[1] & 0xFF);
    }

    public byte getExecuteInstruction(){
        return (byte) (plnInstructions[2] & 0xFF);
    }

    public void setDecodeOperands (byte opCode, byte op1, byte op2){
        //Shift the current values to the right
        plnOpCodes[1] = plnOpCodes[0];
        plnOp1[1] = plnOp1[0];
        plnOp2[1] = plnOp2[0];

        //Set the new values of the next stage
        plnOpCodes[0] = opCode;
        plnOp1[0] = op1;
        plnOp2[0] = op2;
    }

    public byte[] getExecuteData(){
        //return all the data needed for the current execute stage
        return new byte[] {plnOpCodes[1], plnOp1[1], plnOp2[1]};
    }

    public void flushFetchDecode(){
        plnInstructions[0] = -1;
        plnInstructions[1] = -1;
        plnOpCodes[0] = -1;
        plnOp1[0] = -1;
        plnOp2[0] = -1;
    }
}
