package report;

import utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    public record ReportAnalitics(Integer totalLines, Integer totalFrauds, BigDecimal totalAmount) {
        public ReportAnalitics add(TransactionBean current) {
            return new ReportAnalitics(
                totalLines + 1,
                        totalFrauds + (current.isFraud() ? 1 : 0),
                    totalAmount.add(current.amount)

            );
        }
    }

    private record TransactionBean(BigDecimal amount, String fraud) {
        private boolean isFraud() {
            return "1".equals(fraud);
        }
    }

    public ReportAnalitics getReportAnalitcs(String filename){
        Path path = Paths.get(filename);

        try (Stream<String> lines = Files.lines(path)) {
            return lines.skip(1)
                    .map(this::generateReportTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(
                            new ReportAnalitics(0, 0, BigDecimal.ZERO),
                            ReportAnalitics::add,
                            (s1, s2) -> s1
                    );

        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Optional<TransactionBean> generateReportTransaction(String line) {
        String[] values = line.split(",");
        try {
            TransactionBean transaction = new TransactionBean(
                    Utils.convertStringToBigDecimalWithValidationNotNull(values[2], "amount"),
                    values[9]
            );
            return Optional.of(transaction);
        } catch (Exception e) {
            System.err.println("Erro: " + line + " | " + e.getMessage());
            return Optional.empty();
        }
    }
}
