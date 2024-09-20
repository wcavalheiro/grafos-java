package problema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Caminho {
    private double parsec;
    private double custoCombustivel;
    private PontoDeSalto pontoInicial;
    private PontoDeSalto pontoFinal;
}
