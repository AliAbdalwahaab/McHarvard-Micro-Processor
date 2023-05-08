package src.Parts;

public class DataMemory {
    private byte[] data = new byte[2048];
    private int address;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
    public byte getData(int address) {
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
