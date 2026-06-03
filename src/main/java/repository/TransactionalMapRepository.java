package repository;

import model.Transaction;
import service.TransactionIngester;

import java.util.Map;
import java.util.Optional;

public class TransactionalMapRepository implements TransactionRepository{


    @Override
    public Optional<Transaction> findByClientName(String clientName, Integer size, String[] lines) {

        TransactionIngester transactionIngester = new TransactionIngester();
        Map<String, Transaction> transactionsMap = transactionIngester.getTransactionsMapFromFile(size, lines);

        if (transactionsMap.containsKey(clientName)) {
            return Optional.of(transactionsMap.get(clientName));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> findByClientName(String clientName, String[] dataLines) {
        return findByClientName(clientName, dataLines.length - 1, dataLines);
    }

    @Override
    public void save(Transaction transaction) {

    }
}
