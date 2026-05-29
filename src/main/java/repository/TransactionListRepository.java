package repository;

import model.Transaction;
import service.TransactionIngester;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    @Override
    public Optional<Transaction> findByClientName(String clientName, Integer lines) {
        TransactionIngester transactionIngester = new TransactionIngester();
        List<Transaction> transactions = transactionIngester.getTransactionsListFromFile("data/log.csv", lines);

        return transactions.stream()
                .filter(t -> t.originCustomer().name().equals(clientName))
                .findFirst();
    }

}
