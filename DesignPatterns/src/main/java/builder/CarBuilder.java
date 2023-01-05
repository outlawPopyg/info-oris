package builder;

public class CarBuilder implements Builder<Car> {

    private Car car;
    private CarBuilder builder;

    public CarBuilder() { this.builder = null; }

    @Override
    public Builder<Car> reset() {
        car = new Car();
        builder = new CarBuilder();
        return this;
    }

    @Override
    public Builder<Car> setSeats(int count) {
        car.setSeats(count);
        return this;
    }

    @Override
    public Builder<Car> setEngine(String engine) {
        car.setEngine(engine);
        return this;
    }

    @Override
    public Builder<Car> setGPS(boolean b) {
        car.setHaveGPS(b);
        return this;
    }

    @Override
    public Builder<Car> setTripComputer(boolean b) {
        car.setHaveTripComputer(b);
        return this;
    }

    @Override
    public Car getResult() {
        return this.car;
    }
}
