package builder;

public class Car {
    @Override
    public String toString() {
        return "Car{" +
                "seats=" + seats +
                ", engine='" + engine + '\'' +
                ", haveGPS=" + haveGPS +
                ", haveTripComputer=" + haveTripComputer +
                '}';
    }

    private int seats;
    private String engine;
    private boolean haveGPS;
    private boolean haveTripComputer;

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setHaveGPS(boolean haveGPS) {
        this.haveGPS = haveGPS;
    }

    public void setHaveTripComputer(boolean haveTripComputer) {
        this.haveTripComputer = haveTripComputer;
    }
}
