package Components;

public class ALU {
    public SREG sreg;
    private byte operand1;
    private byte operand2;
    private short result;
    private byte opcode;

    public ALU() {

        sreg = new SREG();
    }

    public short getResult() {
        //TODO: storing the results in the correct register to be implemented outside of this class
        //0: Add (R1 = R1 + R2)
        //1: Subtract (R1 = R1 - R2)
        //2: Multiply (R1 = R1 * R2)
        //3: Load immediate (R1 = IMM)
        //4: Branch if equal zero (IF R1 == 0 THEN PC = PC+1+IMM) TODO: return result and use ALU adder outside of this class
        //5: AND (R1 = R1 & R2)
        //6: OR (R1 = R1 | R2)
        //7: JR (PC = R1 concatenated with R2)
        //8: SLC (R1 = R1 << IMM | R1 >>> (8-IMM))
        //9: SRC (R1 = R1 >>> IMM | R1 << (8-IMM))
        //10: Load Byte (R1 = MEM[IMM]) we return the address then we use it to fetch from datamem
        //11: Store Byte (MEM[IMM] = R1) we return the address then we use it to store in datamem

        switch(opcode) {
            case 0: //add
                result = (short) (operand1 + operand2); //we will store the result in R1 after we return it

                //update C, V, N, S, Z flags
                updateCFlag();
                updateVFlag();
                updateNFlag();
                updateSFlag();
                updateZFlag();

                break;
            case 1: //subtract
                result = (short) (operand1 - operand2);
                //update V, N , S , Z flags
                updateVFlag();
                updateNFlag();
                updateSFlag();
                updateZFlag();

                break;
            case 2: //multiply
                result = (short) (operand1 * operand2);
                //update N and Zero flags
                updateNFlag();
                updateZFlag();

                break;
            case 3: //load immediate
                result = operand2;
                break;

            case 4: //branch if equal zero
                if (operand1 == 0) {
                    result = (short) (operand2); //adding the +1 here depends on how we handle pc outside of this class
                } else {
                    result = 255;
                }
                break;

            case 5: //AND
                result = (short) (operand1 & operand2);
                //update N, Z flags
                updateNFlag();
                updateZFlag();

                break;
            case 6: //OR
                result = (short) (operand1 | operand2);
                //update N, Z flags
                updateNFlag();
                updateZFlag();

                break;
            case 7: //JR
                //concatenate operand1 and operand2
                String r = "";
                String op1 = Integer.toBinaryString(operand1);
                String op2 = Integer.toBinaryString(operand2);
                r = op1 + op2;
                result = (short) Integer.parseInt(r);
                break;
            case 8: //SLC
                //TODO: check if this is the correct eq
                result = (short) (operand1 << operand2 | operand1 >>> (8 - operand2));
                //updta N, Z flags
                updateNFlag();
                updateZFlag();

                break;
            case 9: //SRC
                //TODO: check if this is the correct eq
                result = (short) (operand1 >>> operand2 | operand1 << (8 - operand2));
                //update N, Z flags
                updateNFlag();
                updateZFlag();

                break;
            case 10: //load byte
                //address is 6 bits but data mem has 2048 slots
                //the rest of the logic of this operation is to be implemented outside of the ALU
                result = (short) operand2;
                break;
            case 11: //store byte
                //address is 6 bits but data mem has 2048 slots
                //the rest of the logic of this operation is to be implemented outside of the ALU
                result = (short) operand2;
                break;

        }
        return result;
    }

    //methods to update flags
    private void updateCFlag() {
        //C flag
        if (result > 255) { //equivalent to checking 9th bit
            sreg.setCarryFlag((byte) 1);
        } else {
            sreg.setCarryFlag((byte) 0);
        }
    }

    private void updateVFlag() {

        //V flag for add
        if (opcode == 0) {
            if (operand1 > 0 && operand2 > 0 && result < 0) {
                sreg.setOverflowFlag((byte) 1);
            } else if (operand1 < 0 && operand2 < 0 && result > 0) {
                sreg.setOverflowFlag((byte) 1);
            } else {
                sreg.setOverflowFlag((byte) 0);
            }
        } else if (opcode == 1) {
            if (operand1 > 0 && operand2 < 0 && result < 0) {
                sreg.setOverflowFlag((byte) 1);
            } else if (operand1 < 0 && operand2 > 0 && result > 0) {
                sreg.setOverflowFlag((byte) 1);
            } else {
                sreg.setOverflowFlag((byte) 0);
            }
        }
    }

    private void updateNFlag() {
        //N flag
        if (result < 0) {
            sreg.setNegativeFlag((byte) 1);
        } else {
            sreg.setNegativeFlag((byte) 0);
        }
    }

    private void updateSFlag() {
        //S flag
        sreg.setSignFlag((byte) (sreg.getNegativeFlag() ^ sreg.getOverflowFlag()));
    }

    private void updateZFlag() {
        //Z flag
        if (result == 0) {
            sreg.setZeroFlag((byte) 1);
        } else {
            sreg.setZeroFlag((byte) 0);
        }
    }

    //will be used to compute new value of pc in case of branch or jr
    public static short ALUAdder(short pc, byte imm) {
        return (short) (pc + imm);
    }

    //set operands
    public void setOperands(byte operand1, byte operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    //set opcode
    public void setOpcode(byte opcode) {
        this.opcode = opcode;
    }



}