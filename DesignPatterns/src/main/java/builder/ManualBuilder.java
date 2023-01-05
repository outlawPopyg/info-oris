package builder;

public class ManualBuilder implements Builder<CarManual> {

    private CarManual manual;
    public ManualBuilder builder;

    public ManualBuilder() {
        builder = null;
    }

    @Override
    public Builder<CarManual> reset() {
        manual = new CarManual();
        builder = new ManualBuilder();

        return this;
    }

    @Override
    public Builder<CarManual> setSeats(int count) {
        manual.addToDescription("Кол-во сидений: " + count);

        return this;
    }

    @Override
    public Builder<CarManual> setEngine(String engine) {
        manual.addToDescription("Тип двигателя: " + engine);
        return this;
    }

    @Override
    public Builder<CarManual> setGPS(boolean b) {
        manual.addToDescription("Наличие GPS: " + b);
        return this;
    }

    @Override
    public Builder<CarManual> setTripComputer(boolean b) {
        manual.addToDescription("Наличие компьютера: " + b);
        return this;
    }

    @Override
    public CarManual getResult() {
        return this.manual;
    }


}
