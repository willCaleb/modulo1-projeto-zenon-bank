package repository;

import model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> findByClientName(String clientName, Integer size, String[] dataLines);

    Optional<Transaction> findByClientName(String clientName, String[] dataLines);
}
