class Grafo {

    private int[][] matrizAdjacencia;
    private int numNodos;
    private boolean direcionado;

    public Grafo(int numNodos, boolean direcionado) {
        this.numNodos = numNodos;
        this.direcionado = direcionado;
        matrizAdjacencia = new int[numNodos][numNodos];
    }

    /*public void adicionarAresta(int nodo1, int nodo2) {
        if (nodo1 <= numNodos && nodo2 <= numNodos) {
            matrizAdjacencia[nodo1 - 1][nodo2 - 1] = 1;
            if (!direcionado) {
                matrizAdjacencia[nodo2 - 1][nodo1 - 1] = 1;
            }
        } else {
            System.out.println("Nodos inválidos.");
        }
    }*/

    public void adicionarAresta(int nodo1, int nodo2, int peso) {
        if (nodo1 <= numNodos && nodo2 <= numNodos) {
            matrizAdjacencia[nodo1 - 1][nodo2 - 1] = peso;
            if (!direcionado) {
                matrizAdjacencia[nodo2 - 1][nodo1 - 1] = peso;
            }
        } else {
            System.out.println("Nodos inválidos.");
        }
    }

    public void removerAresta(int nodo1, int nodo2) {
        if (nodo1 <= numNodos && nodo2 <= numNodos) {
            matrizAdjacencia[nodo1 - 1][nodo2 - 1] = 0;
            if (!direcionado) {
                matrizAdjacencia[nodo2 - 1][nodo1 - 1] = 0;
            }
            System.out.println("Aresta removida com sucesso.");
        } else {
            System.out.println("Nodos inválidos.");
        }
    }

    public void removerNodo(int nodo) {
        if (nodo <= numNodos) {
            int[][] novaMatriz = new int[numNodos - 1][numNodos - 1];

            for (int i = 0, ni = 0; i < numNodos; i++) {
                if (i == nodo - 1)
                    continue;
                for (int j = 0, nj = 0; j < numNodos; j++) {
                    if (j == nodo - 1)
                        continue;
                    novaMatriz[ni][nj] = matrizAdjacencia[i][j];
                    nj++;
                }
                ni++;
            }

            matrizAdjacencia = novaMatriz;
            numNodos--;
            System.out.println("Nodo removido com sucesso.");
        } else {
            System.out.println("Nodo inválido.");
        }
    }

    public void imprimirMatrizAdjacencia() {
        System.out.println("\nMatriz de Adjacência:");
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void listarGrauNodos() {
        for (int i = 0; i < numNodos; i++) {
            int grauEntrada = 0;
            int grauSaida = 0;

            for (int j = 0; j < numNodos; j++) {
                grauSaida += matrizAdjacencia[i][j];
                grauEntrada += matrizAdjacencia[j][i];
            }

            if (direcionado) {
                System.out.println(
                        "Nodo " + (i + 1) + " - Grau de Entrada: " + grauEntrada + ", Grau de Saída: " + grauSaida);
            } else {
                System.out.println("Nodo " + (i + 1) + " - Grau: " + grauSaida);
            }
        }
    }

    public int[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public void setMatrizAdjacencia(int[][] matrizAdjacencia) {
        this.matrizAdjacencia = matrizAdjacencia;
    }

    public int getNumNodos() {
        return numNodos;
    }

    public void setNumNodos(int novosNodos) {
        if (novosNodos != numNodos) {
            int[][] novaMatriz = new int[novosNodos][novosNodos];

            for (int i = 0; i < Math.min(numNodos, novosNodos); i++) {
                for (int j = 0; j < Math.min(numNodos, novosNodos); j++) {
                    novaMatriz[i][j] = matrizAdjacencia[i][j];
                }
            }
            matrizAdjacencia = novaMatriz;
            numNodos = novosNodos;
        }
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void setDirecionado(boolean direcionado) {
        this.direcionado = direcionado;
    }
}
