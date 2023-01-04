package decorator;

public class CompressedDecorator extends DataSourceDecorator {

    public CompressedDecorator(DataSource source) {
        super(source);
    }

    public void writeData(String data) {
        super.wrappee.writeDate("compressed " + data);
    }

    public String readData() {
        return "decompressed " + super.wrappee.readData();
    }
}
