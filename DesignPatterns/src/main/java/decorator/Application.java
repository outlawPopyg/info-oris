package decorator;

public class Application {
    public static void main(String[] args) {
        DataSource source = new FileDataSource();
        source.writeDate("hello world");

        source = new EncryptionDecorator(source);
        source = new CompressedDecorator(source);

        System.out.println(source.readData());
    }
}
