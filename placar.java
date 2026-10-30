class Placar {
    private String timeCasa;
    private String timeVisitante;
    private int placarCasa;
    private int placarVisitante;

    public Placar(String timeCasa, String timeVisitante) {
        this.timeCasa = timeCasa;
        this.timeVisitante = timeVisitante;
    }

    public int golCasa() {
        placarCasa++;
        return placarCasa;
    }

    public int golVisitante() {
        placarVisitante++;
        return placarVisitante;
    }

    public int anularGolCasa() {
        if (placarCasa > 0) {
            placarCasa--;
        }

        return placarCasa;
    }

    public int anularGolVisitante() {
        if (placarVisitante > 0) {
            placarVisitante--;
        }

        return placarVisitante;
    }

    public String resultado() {
        return timeCasa + " " + placarCasa
                + " x " + placarVisitante
                + " " + timeVisitante;
    }

    public String vencedor() {
        if (placarCasa > placarVisitante) {
            return timeCasa;
        }

        if (placarVisitante > placarCasa) {
            return timeVisitante;
        }

        return "Empate";
    }
}

public class Main {
    public static void main(String[] args) {
        Placar p = new Placar("Tigres", "Leões");

        p.golCasa();
        p.golCasa();
        p.golVisitante();
        p.anularGolVisitante();
        p.anularGolVisitante();

        System.out.println(p.resultado()); // Tigres 2 x 0 Leões
        System.out.println(p.vencedor());  // Tigres
    }
}