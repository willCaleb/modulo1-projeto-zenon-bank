package service;

import repository.TransactionSQLRepository;

public class IngestionMain {

    public static void main(String[] args) {
        EfficientTransactionIngestor transactionIngestor = new EfficientTransactionIngestor();

        TransactionSQLRepository sqlRepository = new TransactionSQLRepository();

        long init = System.currentTimeMillis();
        transactionIngestor.efficientSave("data/log.csv", sqlRepository::saveAll);
        long end = System.currentTimeMillis();

        System.out.println("Tempo total de execução: " + (end - init));
    }

}
