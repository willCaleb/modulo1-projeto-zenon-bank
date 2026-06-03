package db;

import model.Transaction;
import repository.TransactionRepository;
import repository.TransactionSQLRepository;
import service.TransactionIngester;

import java.util.List;
import java.util.Optional;

public class DbMain {


     void main(String[] args) {

         Integer linesToProcess = 10000;
         TransactionIngester transactionIngester = new TransactionIngester();
         String[] dataLines = transactionIngester.getStrings("data/log.csv");
         List<Transaction> transactions = transactionIngester.getTransactionsListFromFile(linesToProcess, dataLines);

         TransactionRepository sqlRepository = new TransactionSQLRepository();




         long init = System.currentTimeMillis();
         transactions.forEach(sqlRepository::save);
         long end = System.currentTimeMillis();

         System.out.println("Tempo total para salvar " + linesToProcess + " foi de " + (end - init));

         Optional<Transaction> optionalTransaction = sqlRepository.findByClientName("C1231006815", 0, null);

         if(optionalTransaction.isPresent()){
             System.out.println(optionalTransaction.get());
         }
    }

}
