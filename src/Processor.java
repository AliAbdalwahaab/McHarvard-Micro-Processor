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
            short instrCount = 0;
            while((line=br.readLine())!=null) {

                String[] instrParts = line.split(" ");
                String instruction = "";

                //opcode
                switch (instrParts[0].toLowerCase()) {
                    case "add":
                        instruction = "0000";
                        break;
                    case "sub":
                        instruction = "0001";
                        break;
                    case "mul":
                        instruction = "0010";
                        break;
                    case "ldi":
                        instruction = "0011";
                        break;
                    case "beqz":
                        instruction = "0100";
                        break;
                    case "and":
                        instruction = "0101";
                        break;
                    case "or":
                        instruction = "0110";
                        break;
                    case "jr":
                        instruction = "0111";
                        break;
                    case "slc":
                        instruction = "1000";
                        break;
                    case "src":
                        instruction = "1001";
                        break;
                    case "lb":
                        instruction = "1010";
                        break;
                    case "sb":
                        instruction = "1011";
                        break;
                    default: //there is a label at the beginning of the instruction
                        isResOfBranch = true;
                        switch (instrParts[1].toLowerCase()) {
                            case "add":
                                instruction = "0000";
                                break;
                            case "sub":
                                instruction = "0001";
                                break;
                            case "mul":
                                instruction = "0010";
                                break;
                            case "ldi":
                                instruction = "0011";
                                break;
                            case "beqz":
                                instruction = "0100";
                                break;
                            case "and":
                                instruction = "0101";
                                break;
                            case "or":
                                instruction = "0110";
                                break;
                            case "jr":
                                instruction = "0111";
                                break;
                            case "slc":
                                instruction = "1000";
                                break;
                            case "src":
                                instruction = "1001";
                                break;
                            case "lb":
                                instruction = "1010";
                                break;
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
                if (!isResOfBranch) {

                    if (instrParts[0].equalsIgnoreCase("add")
                            || instrParts[0].equalsIgnoreCase("sub")
                            || instrParts[0].equalsIgnoreCase("mul")
                            || instrParts[0].equalsIgnoreCase("and")
                            || instrParts[0].equalsIgnoreCase("or")
                            || instrParts[0].equalsIgnoreCase("jr")) {


                        byte op2 = Byte.parseByte(instrParts[2].substring(1));
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);

                    } else if (instrParts[0].equalsIgnoreCase("ldi")
                            || instrParts[0].equalsIgnoreCase("slc")
                            || instrParts[0].equalsIgnoreCase("src")
                            || instrParts[0].equalsIgnoreCase("lb")
                            || instrParts[0].equalsIgnoreCase("sb")) {

                        byte op2 = Byte.parseByte(instrParts[2]);
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);
                    } else { //this is a branch
                      String line2;
                      byte relAdd = 1; //variable that will get us the relative address
                      while ((line2=br.readLine())!=null) {
                          String[] theLine = line.split(" ");
                          if (theLine[0].substring(0,theLine[0].length()-1).equalsIgnoreCase(instrParts[2])) { //i added the substring to exclude the ':' from the comparison
                              byte op2 = relAdd;
                              String op2Str = "" + Integer.toBinaryString(op2);
                              instruction += op2Str.substring(2);
                              break;
                          }
                          relAdd++;
                      }
                    }

                } else {

                    if (instrParts[1].equalsIgnoreCase("add")
                            || instrParts[1].equalsIgnoreCase("sub")
                            || instrParts[1].equalsIgnoreCase("mul")
                            || instrParts[1].equalsIgnoreCase("and")
                            || instrParts[1].equalsIgnoreCase("or")
                            || instrParts[1].equalsIgnoreCase("jr")) {
                        //I am also covering instrParts[1] because
                        // it will correspond to the operation in case this instruction
                        // has a label before it due to a branch

                        byte op2 = Byte.parseByte(instrParts[3].substring(1));
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);

                    } else if (instrParts[1].equalsIgnoreCase("ldi")
                            || instrParts[1].equalsIgnoreCase("slc")
                            || instrParts[1].equalsIgnoreCase("src")
                            || instrParts[1].equalsIgnoreCase("lb")
                            || instrParts[1].equalsIgnoreCase("sb")) {

                        byte op2 = Byte.parseByte(instrParts[3]);
                        String op2Str = "" + Integer.toBinaryString(op2);

                        //add 4 bits of op2 to instruction
                        instruction += op2Str.substring(2);
                    } else {
                        String line2;
                        byte relAdd = 1; //variable that will get us the relative address
                        while ((line2=br.readLine())!=null) {
                            String[] theLine = line.split(" ");
                            if (theLine[0].substring(0,theLine[0].length()-1).equalsIgnoreCase(instrParts[3])) { //i added the substring to exclude the ':' from the comparison
                                byte op2 = relAdd;
                                String op2Str = "" + Integer.toBinaryString(op2);
                                instruction += op2Str.substring(2);
                                break;
                            }
                            relAdd++;
                        }
                    }
                }

                //add the full instruction string to the instruction memory
                short fullInstruction = Short.parseShort(instrParts[3]);
                instructionMem.setAddress(instrCount);
                instructionMem.setInstruction(fullInstruction);
                instrCount++;
            }

            fr.close();
            System.out.println("Parsed all of the instructions");
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
