public class DataAgent {
    private byte[] inBytes = null;

    public DataAgent() {}
    public void released() {
        inBytes = null;
    }

    public byte[] getInBytes() {
        return inBytes;
    }

    public void setInBytes(byte[] inBytes) {
        this.inBytes = inBytes;
    }
}
