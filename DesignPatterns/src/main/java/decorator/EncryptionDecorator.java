package decorator;

public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    public void writeDate(String data) {
        super.wrappee.writeDate("encrypted " + data);
    }

    public String readData() {
        return "decrypted " + super.wrappee.readData();
    }
}
