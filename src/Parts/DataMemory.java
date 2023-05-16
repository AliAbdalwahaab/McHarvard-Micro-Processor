package src.Parts;

public class DataMemory {
    private byte[] data = new byte[2048];
    private short address;
    private boolean memWrite;
    private boolean memRead;

    public void setMemRead(boolean memRead) {
        this.memRead = memRead;
    }

    public void setMemWrite(boolean memWrite) {
        this.memWrite = memWrite;
    }

    public void setData(byte data) {
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

    public short getAddress() {
        return address;
    }

    public void setAddress(short address) {
        this.address = address;
    }
    public byte getData() {
        return data[address];
    }

    public String toString() {
        String r = "Data Memory Contents:\n";
        for (int i = 0; i < data.length; i++) {
            r += "Address: "+i+", Value: "+data[i]+"\n";
        }
        return r;
    }
}
