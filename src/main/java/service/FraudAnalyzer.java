package service;

import enums.EnumTransactionType;
import model.Transaction;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FraudAnalyzer {

    public void printFraudQuantity(List<Transaction> transactions) {
        int size = transactions.stream()
                .filter(Transaction::isFraud)
                .toList()
                .size();
        System.out.println("1. Total de fraudes: " + size);
    }

    public void printMajorFraudsLimit3(List<Transaction> transactions) {
        List<BigDecimal> top3MajorFraudValues = transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(3)
                .map(Transaction::amount)
                .toList();

        System.out.println("2. Top 3 fraudes de maior valor:");
        top3MajorFraudValues.forEach(v -> System.out.println(v.toPlainString()));
    }

    public void printFraudOrigNamesDistinctLimit5(List<Transaction> transactions){
        Set<String> mostFraudstersNames = transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(t -> t.originCustomer().name())
                .distinct()
                .limit(5)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        System.out.println("3. Clientes suspeitos");
        mostFraudstersNames.forEach(System.out::println);
    }

    public void printTotalLoss(List<Transaction> transactions) {
        BigDecimal totalLoss = transactions.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        System.out.println("4. Prejuízo Total: " + totalLoss);
    }

    public void printFraudsQuantityByType(List<Transaction> transactions, EnumTransactionType type) {
        int totalFraudsByType = transactions.stream()
                .filter(Transaction::isFraud)
                .filter(t -> t.type().equals(type))
                .toList()
                .size();
        System.out.println(" - " + type.name() +": " + totalFraudsByType);
    }
}
