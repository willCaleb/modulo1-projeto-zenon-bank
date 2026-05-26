import model.Transaction;
import service.TransactionIngester;

import java.util.List;

public class Main {
    public static void main(String[] args){

        TransactionIngester transactionIngester = new TransactionIngester();

        long init = System.currentTimeMillis();
        List<Transaction> transactionsFromFile = transactionIngester.getTransactionsFromFile("data/log.csv");

        for(int i = 0; i < 10; i++) {
            System.out.println(transactionsFromFile.get(i));
        }

        long end = System.currentTimeMillis();
        System.out.println("Tempo para executar: " + (end - init));

    }
}
