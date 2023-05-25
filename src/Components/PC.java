package Components;

public class PC {
    private short address;

    public short getAddress() {
        return address;
    }

    public void setAddress(short address) {
        this.address = address;
    }

    public String toString() {
        return "PC: " + address;
    }
}
