import model.Transaction;
import service.TransactionIngester;

import java.util.List;

public class Main {
    public static void main(String[] args){

        TransactionIngester transactionIngester = new TransactionIngester();

        long init = System.currentTimeMillis();
        List<Transaction> transactionsFromFile = transactionIngester.getTransactionsFromFile("data/log.csv", 50000);
        List<Transaction> transactionsFromFileWithErrors = transactionIngester.getTransactionsFromFile("data/paysim_with_bad_data.csv", 16);

        for (Transaction transactionsFromFileWithError : transactionsFromFileWithErrors) {
            System.out.println(transactionsFromFileWithError);
        }

        long end = System.currentTimeMillis();
        System.out.println("Tempo para executar: " + (end - init));

    }
}
