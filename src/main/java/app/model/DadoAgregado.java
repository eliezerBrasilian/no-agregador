package app.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dadoagregados")
public class DadoAgregado {

    @Id
    public String id;
    public String type;
    public List<Item> lista;

    public record Item(
            String objectIdentifier,
            double media,
            double mediana,
            int somatorio,
            int contagem,
            double porcentagem) {
    }

    public DadoAgregado() {
    }

    public DadoAgregado(String type, List<Item> lista) {
        this.type = type;
        this.lista = lista;
    }
}
