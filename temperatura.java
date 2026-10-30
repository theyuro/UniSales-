class Termometro {
    private double celsius;

    public Termometro(double celsius) {
        if (celsius < -273.15) {
            throw new IllegalArgumentException(
                "A temperatura não pode ficar abaixo do zero absoluto."
            );
        }

        this.celsius = celsius;
    }

    public double getCelsius() {
        return celsius;
    }

    public double getFahrenheit() {
        return celsius * 9.0 / 5.0 + 32.0;
    }

    public double getKelvin() {
        return celsius + 273.15;
    }

    public boolean setCelsius(double novo) {
        if (novo < -273.15) {
            return false;
        }

        celsius = novo;
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        Termometro t = new Termometro(25);

        System.out.println(t.getFahrenheit());   // 77.0
        System.out.println(t.getKelvin());       // 298.15
        System.out.println(t.setCelsius(-300));  // false
        System.out.println(t.getCelsius());      // 25.0
    }
}