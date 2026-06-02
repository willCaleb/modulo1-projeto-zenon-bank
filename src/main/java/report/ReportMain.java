package report;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;


public class ReportMain {

    void main(String[] args) {

        String language = "PT";

        showAnalitcs(language);
    }

    private void showAnalitcs(String language) {
        TransactionReport transactionReport = new TransactionReport();

        Locale locale = getByLocaleByLanguage(language);

        TransactionReport.ReportAnalitics reportAnalitcs = transactionReport.getReportAnalitcs("data/log.csv");

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        ResourceBundle bundle = getResourceBundleByLocale(getBundlePathByLanguage(language), locale);

        System.out.printf("""
                        %s: %d
                        %s: %d
                        %s %s
                        %n""", bundle.getString("transaction.report.totallinhas"), reportAnalitcs.totalLines(),
                bundle.getString("transaction.report.totalfraudes"), reportAnalitcs.totalFrauds(),
                bundle.getString("transaction.report.valortotal"), numberFormat.format(reportAnalitcs.totalAmount()));
    }

    private String getBundlePathByLanguage(String language) {
        if ("US".equals(language)) {
            return "report_en";
        }
        if ("PT".equals(language)) {
            return "report_pt_BR";
        }
        throw new RuntimeException("Linguagem selecionada não disponível ou inválida");
    }

    private ResourceBundle getResourceBundleByLocale(String bundlePath, Locale locale) {
        return ResourceBundle.getBundle(bundlePath, locale);
    }

    private Locale getByLocaleByLanguage(String language) {
        if ("US".equals(language)) {
            return Locale.US;
        }
        if ("PT".equals(language)) {
            return Locale.of("pt", "BR");
        }
        throw new RuntimeException("Arquivo de propriedades de internacionalização não encontrado");
    }
}
