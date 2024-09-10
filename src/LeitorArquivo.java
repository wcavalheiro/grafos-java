import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorArquivo {

    public static Grafo lerGrafoDeArquivoTxt(String caminhoArquivo) {
        Grafo grafo = null;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String line = br.readLine();
            boolean direcionado = Boolean.parseBoolean(line.trim());

            line = br.readLine();
            int numNodos = Integer.parseInt(line.trim());
            grafo = new Grafo(numNodos, direcionado);

            while ((line = br.readLine()) != null) {
                String[] partes = line.trim().split("\\s+");
                if (partes.length == 2) {
                    int nodo1 = Integer.parseInt(partes[0]);
                    int nodo2 = Integer.parseInt(partes[1]);

                    grafo.adicionarAresta(nodo1, nodo2);
                } else {
                    System.out.println("Formato de linha inválido: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro no formato do arquivo: " + e.getMessage());
        }
        return grafo;
    }
}
