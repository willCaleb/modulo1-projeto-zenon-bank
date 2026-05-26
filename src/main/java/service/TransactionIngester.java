package service;

import enums.EnumTransactionType;
import model.Transaction;
import model.TransactionCustomer;
import model.TransactionReceiverCustomer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionIngester {

    public List<Transaction> getTransactionsFromFile(String filename, Integer linesToProcess) {
        List<Transaction> transactions = new ArrayList<>();
        Path path = Paths.get(filename);

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);

            String content = getContent(channel, buffer);

            String[] lines = content.split("\n");

            return generateTransactionsList(lines, transactions, linesToProcess);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContent(FileChannel channel, ByteBuffer buffer) throws IOException {
        StringBuilder content = new StringBuilder();

        while (channel.read(buffer) != -1) {
            buffer.flip();
            content.append(StandardCharsets.UTF_8.decode(buffer));
            buffer.clear();
        }
        return content.toString();
    }

    private List<Transaction> generateTransactionsList(String[] lines, List<Transaction> transactions, Integer linesToProcess) {
        for (int i = 1; i < linesToProcess; i++) {

            Optional<Transaction> optionalTransaction = generateTransaction(lines[i]);
            optionalTransaction.ifPresent(transactions::add);
        }
        return transactions;
    }

    private Optional<Transaction> generateTransaction(String line) {
        String[] values = line.split(",");
        try {
            Transaction transaction = new Transaction(
                    Integer.parseInt(values[0]),
                    EnumTransactionType.valueOf(values[1]),
                    convertWithValidation(values[2], "amount"),
                    new TransactionCustomer(values[3],
                            convertWithValidation(values[4], "oldBalanceOrg"),
                            convertWithValidation(values[5], "newBalanceOrig")
                            ),
                    new TransactionReceiverCustomer(values[6],
                            convertWithValidation(values[7], "OldBalanceDest"),
                            convertWithValidation(values[8], "newBalanceDest")
                    ),
                    Integer.parseInt(values[9]),
                    Integer.parseInt(values[10])
            );
            return Optional.of(transaction);
        } catch (Exception e) {
            System.err.println("Erro: " + line + " | " + e.getMessage());
            return Optional.empty();
        }
    }

    private BigDecimal convertWithValidation(String strValue, String fieldName) {
        if (strValue == null || strValue.isEmpty()) {
            throw new IllegalArgumentException("O valor de " + fieldName + " não pode ser vazio");
        }
        return new BigDecimal(strValue);
    }

}
