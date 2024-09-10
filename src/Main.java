import excecoes.CaractereInvalido;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws CaractereInvalido {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {
            System.out.println(" --------------------------------------------- ");
            System.out.println(" -------------- MENU DE CRIAÇÃO -------------- ");
            System.out.println(" --------------------------------------------- \n");
            System.out.println(" -------- Escolha a opcao entre 1 a 3 -------- \n");
            System.out.println("1. Criar um Grafo/Digrafo manualmente");
            System.out.println("2. Criar um Grafo/Digrafo a partir de um arquivo .txt");
            System.out.println("3. Sair");
            opcao = sc.nextInt();

            switch (opcao) {

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
                    System.out.print("Digite o caminho do arquivo .txt: ");
                    String caminhoArquivo = sc.next();

                    Grafo grafoDoArquivo = LeitorArquivo.lerGrafoDeArquivoTxt(caminhoArquivo);

                    if (grafoDoArquivo != null) {
                        grafoDoArquivo.imprimirMatrizAdjacencia();
                        grafoDoArquivo.listarGrauVertices();
                    } else {
                        System.out.println("Erro ao carregar o grafo a partir do arquivo.");
                    }
                    break;
                case 3:
                    System.out.println("\n --------------------------------------------- \n");
                    System.out.println("\n ------ OBRIGADO POR USAR NOSSO SISTEMA ------ \n");
                    System.out.println("\n --------------------------------------------- \n");
                    break;
                default:
                    System.out.println("\n Por favor, digite um número válido \n");
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
}



