import enums.EnumTransactionType;
import model.Transaction;
import repository.TransactionListRepository;
import repository.TransactionRepository;
import repository.TransactionalMapRepository;
import service.FraudAnalyzer;
import service.TransactionIngester;

public static void main(String[] args) {

    TransactionIngester transactionIngester = new TransactionIngester();
    TransactionRepository listRepository = new TransactionListRepository();
    TransactionRepository mapRepository = new TransactionalMapRepository();

    List<Transaction> transactionsFromFile = transactionIngester.getTransactionsListFromFile("data/log.csv", 100001);

    String clientName = "C1868032458";

    String notClientName = "C123456";

    Optional<Transaction> transactionExistente = listRepository.findByClientName(clientName, 100001);

    long init1 = System.nanoTime();

    printClientByName(transactionExistente, clientName);
    long end1 = System.nanoTime();

    System.out.println("Tempo de execução lista: " + (end1 - init1));

    Optional<Transaction> optionalTransactionFromMap = mapRepository.findByClientName(clientName, 100001);

    long init2 = System.nanoTime();
    printClientByName(optionalTransactionFromMap, clientName);
    long end2 = System.nanoTime();

    System.out.println("Tempo de execução map: " + (end2 - init2));

}

private static void printClientByName(Optional<Transaction> optionalTransaction, String clientName) {
    if (optionalTransaction.isPresent()) {
        System.out.println(optionalTransaction);
        return;
    }
    System.out.println("Transação não encontrada para o cliente " + clientName);
}

private static void printFraudsByType(FraudAnalyzer fraudAnalyzer, List<Transaction> transactionsFromFile) {
    System.out.println("Fraudes por tipo:");
    fraudAnalyzer.printFraudsQuantityByType(transactionsFromFile, EnumTransactionType.CASH_OUT);
    fraudAnalyzer.printFraudsQuantityByType(transactionsFromFile, EnumTransactionType.TRANSFER);
}
