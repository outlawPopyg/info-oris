package decorator;

public class DataSourceDecorator implements DataSource {

    protected final DataSource wrappee;

    public DataSourceDecorator(DataSource source) {
        wrappee = source;
    }

    @Override
    public void writeDate(String data) {
        wrappee.writeDate(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }
}
