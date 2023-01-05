package builder;

public interface Builder<T> {
    Builder<T> reset();
    Builder<T> setSeats(int count);
    Builder<T> setEngine(String engine);
    Builder<T> setGPS(boolean b);
    Builder<T> setTripComputer(boolean b);
    T getResult();
}
