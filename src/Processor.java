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
        FileReader fr= null;   //reads the file

        try {
            fr = new FileReader(file);
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while((line=br.readLine())!=null)
            {
                String[] splitted = line.split(" ");
                String instruction = "";
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
