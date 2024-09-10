import excecoes.CaractereInvalido;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws CaractereInvalido {
        Scanner sc = new Scanner(System.in);

        int opcao = 0;
        while (opcao != 3){
            System.out.println(" --------------------------------------------- ");
            System.out.println(" -------------- MENU DE CRIAÇÃO -------------- ");
            System.out.println(" --------------------------------------------- \n");
            System.out.println(" -------- Escolha a opcao entre 1 a 3 -------- \n" );
            System.out.println("1. Criar um Grafo/Digrafo");
            System.out.println("2. Criar um Grafo/Digrafo a partir de um arquivo");
            System.out.println("3. Sair");
            opcao = sc.nextInt();

            switch(opcao) {
                case 1:
                    MenuDeCriacao.criarGrafo();
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("\n --------------------------------------------- \n");
                    System.out.println("\n --------------------------------------------- \n");
                    System.out.println("\n ------ OBRIGADO POR USAR NOSSO SISTEMA ------ \n");
                    System.out.println("\n --------------------------------------------- \n");
                    System.out.println("\n --------------------------------------------- \n");
                    break;
                default:
                System.out.println("\n Por favor digite um número válido \n");    
            }
        }
        sc.close();
    }
}


