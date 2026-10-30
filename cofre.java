class Cofre {
    private String senha;
    private int tentativasErradas;
    private boolean bloqueado;

    public Cofre(String senhaInicial) {
        senha = senhaInicial;
        tentativasErradas = 0;
        bloqueado = false;
    }

    public boolean abrir(String tentativa) {
        if (bloqueado == true) {
            System.out.println("O cofre está bloqueado.");
            return false;
        }

        if (tentativa.equals(senha)) {
            tentativasErradas = 0;
            return true;
        } else {
            tentativasErradas = tentativasErradas + 1;

            if (tentativasErradas >= 3) {
                bloqueado = true;
            }

            return false;
        }
    }

    public boolean trocarSenha(String antiga, String nova) {
        if (bloqueado == true) {
            return false;
        }

        if (!antiga.equals(senha)) {
            return false;
        }

        if (nova.length() < 6) {
            return false;
        }

        senha = nova;
        tentativasErradas = 0;
        return true;
    }

    public boolean estaBloqueado() {
        return bloqueado;
    }

    /*
     * Não existe getSenha(), pois a senha não pode sair da classe.
     * Se existisse esse método, qualquer pessoa poderia descobrir
     * a senha sem precisar tentar abrir o cofre.
     */
}

public class Main {
    public static void main(String[] args) {
        Cofre c = new Cofre("dragao42");

        System.out.println(c.abrir("abc"));                   
        // false

        System.out.println(c.abrir("dragao42"));              
        // true

        System.out.println(
            c.trocarSenha("dragao42", "123")
        );
        // false, porque a nova senha tem menos de 6 caracteres

        c.abrir("x");
        c.abrir("y");
        c.abrir("z");

        System.out.println(c.estaBloqueado());
        // true

        System.out.println(c.abrir("dragao42"));
        // false, porque o cofre já está bloqueado
    }
}