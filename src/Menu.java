import java.io.IOException;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean direcionado = false;

        int opcao = 0;
        while (opcao != 3){
            System.out.println("Menu do Programa");
            System.out.println("Escolha a opcao entre 1 a 3");
            System.out.println("1. Criar um grafo");
            System.out.println("2. Criar um digrafo");
            System.out.println("3. Sair");
            opcao = sc.nextInt();

            switch(opcao) {
                case 1:
                    int numNodos = 0;
                    int numArestas = 0;
                    direcionado = false;
                    do{
                        System.out.print("Digite o número de nodos: ");
                        numNodos = sc.nextInt();
                        if(numNodos < 1 || numNodos > 10){
                            System.out.println("Fora do range gurizada");
                        }
                    }while (numNodos <= 1 || numNodos > 10);
                    do{
                        System.out.print("Digite o número de arestas: ");
                        numArestas = sc.nextInt();
                        if(numArestas < 1 || numArestas > 10){
                            System.out.println("Fora do range gurizada");
                        }
                    }while(numArestas <= 1 || numArestas > 10);
                    Grafo grafo = new Grafo(numNodos,direcionado);
                    for (int i = 0; i < numArestas; i++) {
                        System.out.print("Digite a aresta " + (i + 1) + " (formato: nodo1 nodo2): ");
                        int nodo1 = sc.nextInt();
                        int nodo2 = sc.nextInt();

                        grafo.adicionarAresta(nodo1, nodo2);
                    }
                    grafo.imprimirMatrizAdjacencia();
                    grafo.listarGrauVertices();
                    System.in.read();
                    break;
                case 2:
                    numNodos = 0;
                    numArestas = 0;
                    direcionado = true;
                    do{
                        System.out.print("Digite o número de nodos: ");
                        numNodos = sc.nextInt();
                        if(numNodos < 1 || numNodos > 10){
                            System.out.println("Fora do range gurizada");
                        }
                    }while (numNodos <= 1 || numNodos > 10);
                    do{
                        System.out.print("Digite o número de arestas: ");
                        numArestas = sc.nextInt();
                        if(numArestas < 1 || numArestas > 10){
                            System.out.println("Fora do range gurizada");
                        }
                    }while(numArestas <= 1 || numArestas > 10);
                    Grafo digrafo = new Grafo(numNodos,direcionado);
                    for (int i = 0; i < numArestas; i++) {
                        System.out.print("Digite a aresta " + (i + 1) + " (formato: nodo1 nodo2): ");
                        int nodo1 = sc.nextInt();
                        int nodo2 = sc.nextInt();

                        digrafo.adicionarAresta(nodo1, nodo2);
                    }
                    digrafo.imprimirMatrizAdjacencia();
                    digrafo.listarGrauVertices();
                    System.in.read();
                    break;
                case 3:
                    break;    
                default:
                System.out.println("\n Por favor digite um número válido \n");    
            }
        }
        sc.close();
    }
}


