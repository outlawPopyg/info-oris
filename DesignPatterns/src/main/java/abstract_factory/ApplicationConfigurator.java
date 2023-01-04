package abstract_factory;

// Абстрактная фабрика — это порождающий паттерн проектирования, который позволяет создавать семейства связанных объектов,
// не привязываясь к конкретным классам создаваемых объектов.

// В этом примере Абстрактная фабрика создаёт кросс-платформенные элементы интерфейса и следит за тем,
// чтобы они соответствовали выбранной операционной системе.

import java.io.*;

interface Button {
    void paint();
}

class WinButton implements Button {
    public void paint() {
        System.out.println("Windows styled button");
    }
}

class MacButton implements Button {
    public void paint() {
        System.out.println("macOS styled button");
    }
}

interface CheckBox {
    void paint();
}

class WinCheckBox implements CheckBox {
    public void paint() {
        System.out.println("Windows styled check box");
    }
}

class MacCheckBox implements CheckBox {
    public void paint() {
        System.out.println("macOS styled check box");
    }
}

interface GUIFactory {
    Button createButton();
    CheckBox createCheckBox();
}

class WinFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public CheckBox createCheckBox() {
        return new WinCheckBox();
    }
}

class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public CheckBox createCheckBox() {
        return new MacCheckBox();
    }
}

// Для кода, использующего фабрику, не важно, с какой конкретно
// фабрикой он работает. Все получатели продуктов работают с
// ними через общие интерфейсы.
class Application {
    private GUIFactory factory;
    private Button button;

    public Application(GUIFactory factory) {
        this.factory = factory;
    }

    public void createUI() {
        this.button = factory.createButton();
    }

    public void paint() {
        button.paint();
    }
}

// Приложение выбирает тип конкретной фабрики и создаёт её
// динамически, исходя из конфигурации или окружения.
public class ApplicationConfigurator {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("C:\\work\\info-oris\\DesignPatterns\\osinfo.txt")
        );

        String OS = bufferedReader.readLine();
        GUIFactory factory;

        if (OS.equals("windows")) {
            factory = new WinFactory();
        } else if (OS.equals("macOS")) {
            factory = new MacFactory();
        } else {
            throw new IllegalArgumentException("no such os has been matched");
        }

        Application application = new Application(factory);
        application.createUI();
        application.paint();

    }
}
