/*
Aluno: Yuri Giles Santana
Disciplina: Abstração de dados - Eng Software 2 Periodo
Professor: Mykael
Exercicio: Desafio Bichinho Virtual
*/

import java.util.*;
import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {
      System.out.println("Desafio do bichinho - Crie um bichinho que nunca morre de fome por bug: os valores ficam sempre entre 0 e 100.");
    
      Animal bichinho = new Animal(
        "Charizard", 50, 80, 70
      );

      Scanner scanner = new Scanner(System.in);

      int dia = 1;
      
      while (true){

        System.out.println("=== CHARIZARD === " + " === Dia: " + dia + " ===");
        System.out.println("1 - Alimentar");
        System.out.println("2 - Brincar");
        System.out.println("3 - Dormir");
        System.out.println("4 - Status");
        System.out.println("5 - Sair");

        int opcao = scanner.nextInt();

        switch (opcao){
          case 1:
            bichinho.alimentar();
            bichinho.estaFeliz();
            break;
          case 2:
            bichinho.brincar();
            bichinho.estaFeliz();
            break;
          case 3:
            bichinho.dormir();
            dia += 1;
            break;
          case 4:
            System.out.println("Status: ");
            bichinho.status();
            bichinho.estaFeliz();
            break;
          case 5:
            System.out.println("Charizard: Até daqui a pouco Mestre!");
            return ;
            
          default:
            System.out.println("Numero invalido, o bichinho não aprendeu a fazer isso ainda...");
        }

      }
    
    }
}

class Animal{
  private String nome;
  private int fome; 
  private int energia;
  private int felicidade;

  public Animal(String nome, int fome, int energia, int felicidade) {
    this.nome = nome;
    this.fome = Math.max(0, Math.min(100, fome));
    this.energia = Math.max(0, Math.min(100, energia));
    this.felicidade = Math.max(0, Math.min(100, felicidade));
  } 

  public void alimentar(){
    this.fome = Math.max(0, this.fome - 30);
    this.felicidade = Math.min(100, this.felicidade + 5);
    System.out.println("Alimentado ! ");
  }

  public void brincar(){
    if (this.fome == 100){
      System.out.println("Estou doente, não posso brincar.");
      return;
    } else {
    this.felicidade = Math.min(100, this.felicidade + 20);
    this.energia = Math.max(0, this.energia - 15);
    this.fome = Math.min(100, this.fome +10);
    System.out.println("Brincou ! ");
    }
  }

  public void dormir(){
    this.energia = 100;
    this.fome = Math.min(100, this.fome + 20);
    System.out.println("Tirando uma pestana zZzZzZzZzZzZzZz ");
  }

  public boolean estaFeliz(){
    if (this.felicidade >= 70){
      System.out.println(this.nome + " Está feliz!");
      return true;
    } else {
      System.out.println(this.nome + " Está triste! Felicidade menor que 70");
      return false;
    }
  }

  public void status(){
    System.out.println(this.nome + ":");
    System.out.println("Felicidade: " + this.felicidade);
    System.out.println("Fome: " + this.fome);
    System.out.println("Energia: " + this.energia);
  }

}



