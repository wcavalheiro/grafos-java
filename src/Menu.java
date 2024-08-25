import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número de nodos: ");
        int numNodos = scanner.nextInt();

        System.out.print("Digite o número de arestas: ");
        int numArestas = scanner.nextInt();

        Grafo grafo = new Grafo(numNodos);

        for (int i = 0; i < numArestas; i++) {
            System.out.print("Digite a aresta " + (i + 1) + " (formato: nodo1 nodo2): ");
            int nodo1 = scanner.nextInt();
            int nodo2 = scanner.nextInt();

            grafo.adicionarAresta(nodo1, nodo2);
        }

        grafo.imprimirMatrizAdjacencia();
        scanner.close();
    }

}