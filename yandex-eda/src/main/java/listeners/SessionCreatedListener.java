package listeners;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import models.Product;
import models.User;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionCreatedListener implements HttpSessionListener {

    public static Set<User> AUTH_USERS = new HashSet<>();
    public static Integer ACTIVE_COUNT = 0;

    public static Boolean IS_AUTH = false;

    private static List<Product> ACTIVE_ORDERS = new LinkedList<>();

    public static List<Product> TAKE = new LinkedList<>();

    public static void addOrder(Product product) {
        ACTIVE_ORDERS.add(product);
        ACTIVE_COUNT++;
    }

    public static void deleteOrder(int index) {
        ACTIVE_ORDERS.remove(index);
        ACTIVE_COUNT--;
    }

    public static List<Product> getActiveOrders() {
        return ACTIVE_ORDERS;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session destroyed");
    }
}
