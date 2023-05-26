package Components;

public class DataMemory {
    private byte[] data = new byte[2048];
    private boolean memWrite;
    private boolean memRead;

    public void setMemRead(boolean memRead) {
        this.memRead = memRead;
    }

    public void setMemWrite(boolean memWrite) {
        this.memWrite = memWrite;
    }

    public void setData(short address, byte data) {
        if (memWrite) {
            this.data[address] = data;
        }
    }

    public boolean isMemRead() {
        return memRead;
    }

    public boolean isMemWrite() {
        return memWrite;
    }

    public byte getData(short address) {
        return data[address];
    }

    public String toString() {
        String r = "Data Memory Contents:\n";
        for (int i = 0; i < data.length; i++) {
            r += "Address: "+i+", Value: "+data[i]+"\n";
        }
        return r;
    }

    public void printData() {
        System.out.println("=====================================");
        System.out.println("Data Memory Contents:");
        for (int i = 0; i < data.length; i++) {
            System.out.println("Address " + i + ": " + data[i]);
        }
        System.out.println("=====================================");
    }
}
