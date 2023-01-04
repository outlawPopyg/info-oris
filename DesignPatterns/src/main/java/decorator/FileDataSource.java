package decorator;

public class FileDataSource implements DataSource {

    private String data;

    public FileDataSource() {
        data = "there's no data";
    }

    @Override
    public void writeDate(String data) {
        this.data = data;
    }

    @Override
    public String readData() {
        return this.data;
    }
}
