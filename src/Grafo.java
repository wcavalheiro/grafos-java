import java.util.Scanner;

class Grafo {
    private int[][] matrizAdjacencia;
    private int numNodos;

    // Construtor
    public Grafo(int numNodos) {
        this.numNodos = numNodos;
        matrizAdjacencia = new int[numNodos][numNodos];
    }

    // Método para adicionar uma aresta
    public void adicionarAresta(int nodo1, int nodo2) {
        matrizAdjacencia[nodo1 - 1][nodo2 - 1] = 1;
        matrizAdjacencia[nodo2 - 1][nodo1 - 1] = 1; // Para grafos não direcionados
    }

    // Método para imprimir a matriz de adjacência
    public void imprimirMatrizAdjacencia() {
        System.out.println("Matriz de Adjacência:");
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }
}