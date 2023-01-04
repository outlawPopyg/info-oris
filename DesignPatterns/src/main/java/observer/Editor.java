package observer;

import java.io.File;

public class Editor {
    public EventManager eventManager;
    private File file;

    public Editor() {
        eventManager = new EventManager("save", "open");
    }

    public void openFile(String path) {
        this.file = new File(path);
        eventManager.notifyListeners("open", file);
    }

    public void saveFile() {
        if (file != null) {
            eventManager.notifyListeners("save", file);
        } else {
            throw new IllegalArgumentException("Open file first");
        }
    }
}
