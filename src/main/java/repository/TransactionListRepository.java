package repository;

import model.Transaction;
import service.TransactionIngester;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    @Override
    public Optional<Transaction> findByClientName(String clientName, Integer linesToProcess, String[] dataLines) {
        TransactionIngester transactionIngester = new TransactionIngester();
        List<Transaction> transactions = transactionIngester.getTransactionsListFromFile(linesToProcess, dataLines);

        return transactions.stream()
                .filter(t -> t.originCustomer().name().equals(clientName))
                .findFirst();
    }

    @Override
    public Optional<Transaction> findByClientName(String clientName, String[] dataLines) {
        return findByClientName(clientName, dataLines.length - 1, dataLines);
    }

}
