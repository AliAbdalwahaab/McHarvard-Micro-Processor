package src;

import src.Parts.ALU;
import src.Parts.DataMemory;
import src.Parts.InstructionMemory;
import src.Parts.RegisterFile;

import java.io.*;

public class Processor {
    static int cycles = 1;
    static short pc = 0;
    static ALU alu = new ALU();
    static RegisterFile regFile = new RegisterFile();
    static DataMemory dataMem = new DataMemory();
    static InstructionMemory instructionMem = new InstructionMemory();


    public static void fetch() {

    }

    public static void decode() {

    }

    public static void execute() {
        //TODO: we need to remember to handle the case that if a branch succeeds we need to handle aborting the instructions in the IF and ID stages

    }

    public void parse(String fileName) {
        File file=new File(fileName);    //creates a new file instance
        FileReader fr;   //reads the file

        try {
            fr = new FileReader(file);
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            boolean isResOfBranch = false; //will be true if there is a label at the beginning of an instruction
            while((line=br.readLine())!=null) {
                String[] instrParts = line.split(" ");
                String instruction = "";

                //opcode
                switch (instrParts[0]) {
                    case "ADD":
                    case "add":
                        instruction = "0000";
                        break;
                    case "SUB":
                    case "sub":
                        instruction = "0001";
                        break;
                    case "MUL":
                    case "mul":
                        instruction = "0010";
                        break;
                    case "LDI":
                    case "ldi":
                        instruction = "0011";
                        break;
                    case "BEQZ":
                    case "beqz":
                        instruction = "0100";
                        break;
                    case "AND":
                    case "and":
                        instruction = "0101";
                        break;
                    case "OR":
                    case "or":
                        instruction = "0110";
                        break;
                    case "JR":
                    case "jr":
                        instruction = "0111";
                        break;
                    case "SLC":
                    case "slc":
                        instruction = "1000";
                        break;
                    case "SRC":
                    case "src":
                        instruction = "1001";
                        break;
                    case "LB":
                    case "lb":
                        instruction = "1010";
                        break;
                    case "SB":
                    case "sb":
                        instruction = "1011";
                        break;
                    default: //there is a label at the beginning of the instruction
                        isResOfBranch = true;
                        switch (instrParts[1]) {
                            case "ADD":
                            case "add":
                                instruction = "0000";
                                break;
                            case "SUB":
                            case "sub":
                                instruction = "0001";
                                break;
                            case "MUL":
                            case "mul":
                                instruction = "0010";
                                break;
                            case "LDI":
                            case "ldi":
                                instruction = "0011";
                                break;
                            case "BEQZ":
                            case "beqz":
                                instruction = "0100";
                                break;
                            case "AND":
                            case "and":
                                instruction = "0101";
                                break;
                            case "OR":
                            case "or":
                                instruction = "0110";
                                break;
                            case "JR":
                            case "jr":
                                instruction = "0111";
                                break;
                            case "SLC":
                            case "slc":
                                instruction = "1000";
                                break;
                            case "SRC":
                            case "src":
                                instruction = "1001";
                                break;
                            case "LB":
                            case "lb":
                                instruction = "1010";
                                break;
                            case "SB":
                            case "sb":
                                instruction = "1011";
                                break;
                        }
                        break;
                }

                //operand1
                if (!isResOfBranch) {
                    byte op1 = Byte.parseByte(instrParts[1].substring(1));
                    String op1Str = "" + Integer.toBinaryString(op1);

                    //add 4 bits of op1 to instruction
                    instruction += op1Str.substring(2);
                } else {
                    byte op1 = Byte.parseByte(instrParts[2].substring(1));
                    String op1Str = "" + Integer.toBinaryString(op1);

                    //add 4 bits of op1 to instruction
                    instruction += op1Str.substring(2);
                }

                //operand2
                //it is another register in case of ADD, SUB, MUL, AND, OR, JR
                //it is an immediate value in case of LDI, BEQZ, SLC, SRC, LB, SB
                if (isResOfBranch) {

                    if (instrParts[0].equals("ADD") || instrParts[0].equals("add")
                            || instrParts[0].equals("SUB") || instrParts[0].equals("sub")
                            || instrParts[0].equals("MUL") || instrParts[0].equals("mul")
                            || instrParts[0].equals("AND") || instrParts[0].equals("and")
                            || instrParts[0].equals("OR") || instrParts[0].equals("or")
                            || instrParts[0].equals("JR") || instrParts[0].equals("jr")) {


                        byte op2 = Byte.parseByte(instrParts[2].substring(1));
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);

                    } else if (instrParts[0].equals("LDI") || instrParts[0].equals("ldi")
                            || instrParts[0].equals("SLC") || instrParts[0].equals("slc")
                            || instrParts[0].equals("SRC") || instrParts[0].equals("src")
                            || instrParts[0].equals("LB") || instrParts[0].equals("lb")
                            || instrParts[0].equals("SB") || instrParts[0].equals("sb")) {

                        byte op2 = Byte.parseByte(instrParts[2]);
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);
                    }

                } else {

                    if (instrParts[1].equals("ADD") || instrParts[1].equals("add")
                            || instrParts[1].equals("SUB") || instrParts[1].equals("sub")
                            || instrParts[1].equals("MUL") || instrParts[1].equals("mul")
                            || instrParts[1].equals("AND") || instrParts[1].equals("and")
                            || instrParts[1].equals("OR") || instrParts[1].equals("or")
                            || instrParts[1].equals("JR") || instrParts[1].equals("jr")) {
                        //I am also covering instrParts[1] because
                        // it will correspond to the operation in case this instruction
                        // has a label before it due to a branch

                        byte op2 = Byte.parseByte(instrParts[3].substring(1));
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);

                    } else if (instrParts[1].equals("LDI") || instrParts[1].equals("ldi")
                            || instrParts[1].equals("SLC") || instrParts[1].equals("slc")
                            || instrParts[1].equals("SRC") || instrParts[1].equals("src")
                            || instrParts[1].equals("LB") || instrParts[1].equals("lb")
                            || instrParts[1].equals("SB") || instrParts[1].equals("sb")) {

                        byte op2 = Byte.parseByte(instrParts[3]);
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);
                    }
                }
            }

            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {


        //parse the .txt file

        while (instructionMem.getInstruction(pc) != 0) {

            short oldPc = pc;

            short instruction = instructionMem.getInstruction(pc);
            pc++;

            if (oldPc == 0) { //first instruction so I will only fetch cycle

            } else { //i will fetch, decode, execute then print cycle

            }


        }
        //System.out.println("Hello World!");
    }
}
