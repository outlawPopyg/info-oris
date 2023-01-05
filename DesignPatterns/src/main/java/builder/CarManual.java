package builder;

public class CarManual {
    private String description = "";

    public void addToDescription(String s) {
        description += s + '\n';
    }
    public String getInfo() { return description; }
}
