

import java.util.InputMismatchException;
import java.util.Scanner;



class OperacaoInvalidaException extends Exception {
public OperacaoInvalidaException(String mensagem) {
super(mensagem);
}
}


public class Main{


public static int calcular(int a, int b, char operacao) throws OperacaoInvalidaException {
if (operacao == '+') {
return a + b;
} else if (operacao == '-') {
return a - b;
} else if (operacao == '*') {
return a * b;
} else if (operacao == '/') {
return a / b;
} else {
throw new OperacaoInvalidaException("Operação inválida!");
}
}


public static void main(String[] args) {
int[] numero = {10, 0, 25, 4, -3, 8};
Scanner scanner = new Scanner(System.in);


try {
System.out.print("Digite a posição 1: ");
int numero1 = scanner.nextInt();


System.out.print("Digite a posição 2: ");
int numero2 = scanner.nextInt();


System.out.print("Digite a operação (+, -, *, /): ");
char operacao = scanner.next().charAt(0);


int resultado = calcular(numero[numero1], numero[numero2], operacao);
System.out.println("Resultado: " + resultado);


} catch (ArrayIndexOutOfBoundsException e) {
System.out.println("Erro: índice inválido!");
} catch (ArithmeticException e) {
System.out.println("Erro: divisão por zero!");
} catch (InputMismatchException e) {
System.out.println("Erro: digite um número inteiro!");
} catch (OperacaoInvalidaException e) {
System.out.println("Erro: " + e.getMessage());
}
}
}
