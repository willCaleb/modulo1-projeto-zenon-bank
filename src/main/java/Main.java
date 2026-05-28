import enums.EnumTransactionType;
import model.Transaction;
import service.FraudAnalyzer;
import service.TransactionIngester;

import java.util.List;

public class Main {
    public static void main(String[] args){

        TransactionIngester transactionIngester = new TransactionIngester();
        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer();

        List<Transaction> transactionsFromFile = transactionIngester.getTransactionsFromFile("data/log.csv", 50000);

//        List<Transaction> transactionsFromFileWithErrors = transactionIngester.getTransactionsFromFile("data/paysim_with_bad_data.csv", 16);
//
//        for (Transaction transactionsFromFileWithError : transactionsFromFileWithErrors) {
//            System.out.println(transactionsFromFileWithError);
//        }

        fraudAnalyzer.printFraudQuantity(transactionsFromFile);
        fraudAnalyzer.printMajorFraudsLimit3(transactionsFromFile);
        fraudAnalyzer.printFraudOrigNamesDistinctLimit5(transactionsFromFile);
        fraudAnalyzer.printTotalLoss(transactionsFromFile);

        System.out.println("Fraudes por tipo:");
        fraudAnalyzer.printFraudsQuantityByType(transactionsFromFile, EnumTransactionType.CASH_OUT);
        fraudAnalyzer.printFraudsQuantityByType(transactionsFromFile, EnumTransactionType.TRANSFER);

    }
}
