package repository;

import model.Transaction;
import service.TransactionIngester;

import java.util.Map;
import java.util.Optional;

public class TransactionalMapRepository implements TransactionRepository{


    @Override
    public Optional<Transaction> findByClientName(String clientName, Integer size) {

        TransactionIngester transactionIngester = new TransactionIngester();
        Map<String, Transaction> transactionsMap = transactionIngester.getTransactionsMapFromFile("data/log.csv", size);

        if (transactionsMap.containsKey(clientName)) {
            return Optional.of(transactionsMap.get(clientName));
        }
        return Optional.empty();
    }
}
