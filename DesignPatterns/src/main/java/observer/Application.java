package observer;

public class Application {
    public static void main(String[] args) {
        Editor editor = new Editor();

        editor.eventManager.subscribe("open", (eventType, file) -> System.out.println("Opened #1 " + file.getName()));

        editor.eventManager.subscribe("save", (eventType, file) -> System.out.println("Saved #1 " + file.getName()));

        editor.eventManager.subscribe("open", (eventType, file) -> System.out.println("Opened #2"));

        editor.openFile("C:/work/info-oris/DesignPatterns/log.txt");
        editor.saveFile();
    }
}
