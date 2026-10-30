interface Forma {
    double area();
    double perimetro();
    String nome();
}

class Retangulo implements Forma {
    private double base;
    private double altura;

    public Retangulo(double base, double altura) {
        if (base <= 0 || altura <= 0) {
            throw new IllegalArgumentException(
                "As medidas devem ser positivas"
            );
        }

        this.base = base;
        this.altura = altura;
    }

    public double area() {
        return base * altura;
    }

    public double perimetro() {
        return 2 * base + 2 * altura;
    }

    public String nome() {
        return "Retângulo";
    }
}

public class Main {
    public static void main(String[] args) {
        Forma forma = new Retangulo(3, 4);

        System.out.printf(
            "%s: área %.2f, perímetro %.2f%n",
            forma.nome(),
            forma.area(),
            forma.perimetro()
        );
    }
}