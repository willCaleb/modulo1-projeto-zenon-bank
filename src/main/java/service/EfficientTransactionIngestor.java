package service;

import enums.EnumTransactionType;
import model.Transaction;
import model.TransactionCustomer;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor {


    public static final Integer BATCH_SIZE = 1000;

    public void efficientSave(String filename, Consumer<List<Transaction>> consumer) {
        Path path = Paths.get(filename);

        try( ExecutorService executorService = Executors.newFixedThreadPool(10);
                Stream<String> linesStream = Files.lines(path).skip(1)) {

            Iterator<String> iterator = linesStream.iterator();

            List<String> lines = new ArrayList<>(BATCH_SIZE);

            while (iterator.hasNext()) {
                String line = iterator.next();

                lines.add(line);

                if (lines.size() == BATCH_SIZE) {
                    List<String> copyBatch = List.copyOf(lines);

                    executorService.submit(()-> {
                        try{
                            execute(copyBatch, consumer);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    lines.clear();
                }
            }

            if (!lines.isEmpty()) {
                List<String> copyBatch = List.copyOf(lines);

                executorService.submit(()-> {
                    try{
                        execute(copyBatch, consumer);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void execute(List<String> batch, Consumer<List<Transaction>> consumer) {
        List<Transaction> transactionList = batch.stream()
                .map(this::generateTransaction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        consumer.accept(transactionList);
    }

    private Optional<Transaction> generateTransaction(String line) {
        String[] values = line.split(",");
        try {
            Transaction transaction = new Transaction(
                    Integer.parseInt(values[0]),
                    EnumTransactionType.valueOf(values[1]),
                    Utils.convertStringToBigDecimalWithValidationNotNull(values[2], "amount"),
                    new TransactionCustomer(values[3],
                            Utils.convertStringToBigDecimalWithValidationNotNull(values[4], "oldBalance"),
                            Utils.convertStringToBigDecimalWithValidationNotNull(values[5], "newBalance")
                    ),
                    new TransactionCustomer(values[6],
                            Utils.convertStringToBigDecimalWithValidationNotNull(values[7], "OldBalance"),
                            Utils.convertStringToBigDecimalWithValidationNotNull(values[8], "newBalance")
                    ),
                    values[9].equals("1"),
                    values[10].equals("1")
            );
            return Optional.of(transaction);
        } catch (Exception e) {
            System.err.println("Erro: " + line + " | " + e.getMessage());
            return Optional.empty();
        }
    }

}
