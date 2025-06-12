package app.core;

import java.util.List;

public record OverallAggregatedResults(
        // votacao e iot
        List<TypeSpecificAggregatedResult> dadosAgregados,
        long totalLotesProcessadosGlobal,
        long totalItensDeDadosProcessadosGlobal) {

    public record TypeSpecificAggregatedResult(
            String type,
            // objeto com o candidato a...
            List<AggregatedStatisticItem> lista) {
    }

    public record AggregatedStatisticItem(
            String objectIdentifier,
            double media,
            double mediana,
            double somatorio,
            long contagem,
            double porcentagem) {
    }

}
