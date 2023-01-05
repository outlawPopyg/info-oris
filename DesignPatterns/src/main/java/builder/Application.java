package builder;

public class Application {
    public static void main(String[] args) {
        CarManual manual = new ManualBuilder()
                .reset()
                .setGPS(false)
                .setEngine("Default")
                .setTripComputer(false)
                .setSeats(5)
                .getResult();

        Car car = new CarBuilder()
                .reset()
                .setEngine("Sport")
                .setSeats(5)
                .getResult();

        System.out.println(manual.getInfo());
        System.out.println(car);


    }
}
