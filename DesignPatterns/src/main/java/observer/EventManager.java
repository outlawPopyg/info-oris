package observer;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventManager {
    private final Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(String ... eventTypes) {
        for (String eventType : eventTypes) {
            listeners.put(eventType, new LinkedList<>());
        }
    }

    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        eventListeners.add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        eventListeners.remove(listener);
    }

    public void notifyListeners(String eventType, File file) {
        List<EventListener> eventListeners = listeners.get(eventType);
        for (EventListener listener : eventListeners) {
            listener.update(eventType, file);
        }
    }
}
