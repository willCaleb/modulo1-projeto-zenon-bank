package report;

public class ReportMain {

    public static void main(String[] args) {
        TransactionReport transactionReport = new TransactionReport();

        TransactionReport.ReportAnalitics reportAnalitcs = transactionReport.getReportAnalitcs("data/log.csv");

        System.out.println("""
                Total de linhas: %d
                Total de fraudes: %d
                Valor total transacionado: %f
                """.formatted(reportAnalitcs.totalLines(), reportAnalitcs.totalFrauds(), reportAnalitcs.totalAmount()));

//        Stream<String> linesStream = transactionReport.getLinesStream("data/log.csv");
//
//        System.out.println(linesStream.count());
    }



}
