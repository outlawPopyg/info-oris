package adapter;
// Адаптер — это структурный паттерн проектирования, который позволяет объектам с несовместимыми интерфейсами работать вместе.

// В этом шуточном примере Адаптер преобразует один интерфейс в другой, позволяя совместить квадратные колышки и круглые отверстия.

class RoundHole {
    private double radius;

    public RoundHole(double radius) { this.radius = radius; }

    private double getRadius() {
        return radius;
    }

    public boolean fits(RoundPeg roundPeg) {
        return radius >= roundPeg.getRadius();
    }
}

// Круглый колышек
class RoundPeg {
    private double radius;

    public RoundPeg() {};

    public RoundPeg(double radius) { this.radius = radius; }

    public double getRadius() { return radius; }
}

// Квадратный колышек
class SquarePeg {
    private double width;

    public SquarePeg(double width) { this.width = width; }

    public double getWidth() { return width; }
}

class SquarePegAdapter extends RoundPeg {
    private SquarePeg squarePeg;

    public SquarePegAdapter(SquarePeg squarePeg) {
        this.squarePeg = squarePeg;
    }

    @Override
    public double getRadius() {
        return Math.sqrt(Math.pow((squarePeg.getWidth() / 2), 2) * 2);
    }
}

public class Application {
    public static void main(String[] args) {
        RoundHole hole = new RoundHole(5);

        RoundPeg roundPeg = new RoundPeg(5);
        System.out.println(hole.fits(roundPeg)); // true

        SquarePeg squarePeg = new SquarePeg(10);
//        System.out.println(hole.fits(squarePeg)); error

        SquarePegAdapter squarePegAdapter = new SquarePegAdapter(squarePeg);
        System.out.println(hole.fits(squarePegAdapter)); // false
    }
}
