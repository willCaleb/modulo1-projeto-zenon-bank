package service;

import enums.EnumTransactionType;
import model.Transaction;

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

public class TransactionIngester {

    public List<Transaction> getTransactionsFromFile(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        Path path = Paths.get(filename);

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);

            String content = getContent(channel, buffer);

            String[] lines = content.split("\n");

            return generateTransactions(lines, transactions);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContent(FileChannel channel, ByteBuffer buffer) throws IOException {
        StringBuilder content = new StringBuilder();

        while( channel.read(buffer) != - 1) {
            buffer.flip();
            content.append(StandardCharsets.UTF_8.decode(buffer));
            buffer.clear();
        }
        return content.toString();
    }

    private List<Transaction> generateTransactions(String[] lines, List<Transaction> transactions) {
        for(int i = 1; i < 1000; i++) {
            String[] values = lines[i].split(",");

                Transaction transaction = new Transaction(
                        Integer.parseInt(values[0]),
                        EnumTransactionType.valueOf(values[1]),
                        new BigDecimal(values[2]),
                        values[3],
                        new BigDecimal(values[4]),
                        new BigDecimal(values[5]),
                        values[6],
                        new BigDecimal(values[7]),
                        new BigDecimal(values[8]),
                        Integer.parseInt(values[9]),
                        Integer.parseInt(values[10])
                );

                transactions.add(transaction);
        }
        return transactions;
    }
}
