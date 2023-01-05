package singleton;

class Database {
    private static Database instance;
    private Database() {
        System.out.println("[Database connection...]");
    }

    public static Database getInstance() {
        synchronized (Database.class) {
            if (instance == null) {
                instance = new Database();
            }

            return instance;
        }
    }

    public void query(String sql) {
        System.out.println("[resolving " + sql + "...]");
        System.out.println("[ { id: 1, name: 'Kalim'}, {id: 2, name: 'Ilnaz' } ]");
    }
}

public class Application {
    public static void main(String[] args) {

        new Thread(() -> {
            Database foo = Database.getInstance();
            foo.query("select * from users");
        }).start();

        new Thread(() -> {
            Database bar = Database.getInstance();
            bar.query("select * from admins");
        }).start();


    }
}


