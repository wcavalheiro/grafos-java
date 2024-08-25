class Grafo {
    private int[][] matrizAdjacencia;
    private int numNodos;
    private boolean direcionado;

    // Construtor
    public Grafo(int numNodos, boolean direcionado) {
        this.numNodos = numNodos;
        this.direcionado = direcionado;
        matrizAdjacencia = new int[numNodos][numNodos];
    }

    // Método para adicionar uma aresta
    public void adicionarAresta(int nodo1, int nodo2) {
        matrizAdjacencia[nodo1 - 1][nodo2 - 1] = 1;
        if (!direcionado) {
            matrizAdjacencia[nodo2 - 1][nodo1 - 1] = 1; // Para grafos não direcionados
        }
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
    public void listarGrauVertices() {
        for (int i = 0; i < numNodos; i++) {
            int grauEntrada = 0;
            int grauSaida = 0;

            for (int j = 0; j < numNodos; j++) {
                grauSaida += matrizAdjacencia[i][j];
                grauEntrada += matrizAdjacencia[j][i];
            }

            if (direcionado) {
                System.out.println(
                        "Vértice " + (i + 1) + " - Grau de Entrada: " + grauEntrada + ", Grau de Saída: " + grauSaida);
            } else {
                System.out.println("Vértice " + (i + 1) + " - Grau: " + grauSaida);
            }
        }
    }
}