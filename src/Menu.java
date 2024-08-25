import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número de nodos: ");
        int numNodos = scanner.nextInt();

        System.out.print("Digite o número de arestas: ");
        int numArestas = scanner.nextInt();
        int[][] matrizAdjacencia = new int[numNodos][numNodos];

        for (int i = 0; i < numArestas; i++) {
            System.out.print("Digite a aresta " + (i + 1) + " (formato: nodo1 nodo2): ");
            int nodo1 = scanner.nextInt();
            int nodo2 = scanner.nextInt();

            matrizAdjacencia[nodo1 - 1][nodo2 - 1] = 1;
            matrizAdjacencia[nodo2 - 1][nodo1 - 1] = 1;
        }

        System.out.println("Matriz de Adjacência:");
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();

    }
}