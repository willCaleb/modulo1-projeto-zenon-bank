package service;

import model.Transaction;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngester {

    public List<Transaction> getTransactionsFromFile(String filename) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        Path path = Paths.get(filename);

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);

            while(channel.read(buffer) != - 1) {
                buffer.flip();
                buffer.clear();
            }
        }
        return transactions;
    }


}
