package com.iispl.dto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.iispl.connectionpool.ConnectionPool;
import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeStatus;
import com.iispl.model.Cheque;

public class ChequedtoIMPL implements ChequeDTO {

    @Override
    public List<Cheque> getAllCheques() {

        List<Cheque> chequeList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM cheque");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Cheque cheque = new Cheque();

                cheque.setChequeNumber(resultSet.getString("cheque_number"));
                cheque.setAccountNumber(resultSet.getString("account_number"));
                cheque.setDrawerName(resultSet.getString("drawer_name"));
                cheque.setPresentingBank(resultSet.getString("presenting_bank"));
                cheque.setChequeAmount(resultSet.getBigDecimal("cheque_amount"));
                cheque.setChequeDate(resultSet.getDate("cheque_date").toLocalDate());
                cheque.setPresentedDate(resultSet.getDate("presented_date").toLocalDate());

                cheque.setPriority(
                        ChequePriority.valueOf(
                                resultSet.getString("priority").trim().toUpperCase()));

                cheque.setStatus(
                        ChequeStatus.valueOf(
                                resultSet.getString("status").trim().toUpperCase()));

                chequeList.add(cheque);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chequeList;
    }

    @Override
    public boolean isChequeNumberExists(String chequeNumber) {

        String sql = "SELECT COUNT(*) AS total FROM cheque WHERE cheque_number=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, chequeNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total") > 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void updateChequeStatus(Cheque cheque) {

        String sql =
                "UPDATE cheque SET status=?, priority=? WHERE cheque_number=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cheque.getStatus().name());
            preparedStatement.setString(2, cheque.getPriority().name());
            preparedStatement.setString(3, cheque.getChequeNumber());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList) {

        chequeList.sort(
                Comparator.comparing(Cheque::getPresentingBank)
                          .thenComparing(Cheque::getChequeAmount));

        System.out.printf(
                "%-15s %-15s %-20s %-20s %-15s %-15s %-15s %-12s %-12s%n",
                "Cheque No",
                "Account No",
                "Drawer Name",
                "Presenting Bank",
                "Amount",
                "Cheque Date",
                "Presented Date",
                "Priority",
                "Status");

        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------");

        chequeList.forEach(cheque ->
                System.out.printf(
                        "%-15s %-15s %-20s %-20s %-15s %-15s %-15s %-12s %-12s%n",
                        cheque.getChequeNumber(),
                        cheque.getAccountNumber(),
                        cheque.getDrawerName(),
                        cheque.getPresentingBank(),
                        cheque.getChequeAmount(),
                        cheque.getChequeDate(),
                        cheque.getPresentedDate(),
                        cheque.getPriority(),
                        cheque.getStatus()));
    }

    @Override
    public void sortChequeByDate(List<Cheque> chequeList) {

        chequeList.sort(Comparator.comparing(Cheque::getChequeDate));

        chequeList.forEach(System.out::println);
    }

    @Override
    public void displayHighValuedCheques(List<Cheque> chequeList) {

        for (Cheque cheque : chequeList) {

            if (cheque.getPriority() == ChequePriority.HIGH) {

                System.out.println(cheque);
                System.out.println("--------------------------------------------");
            }
        }
    }

    @Override
    public void sortByAmountDescending(List<Cheque> allCheques) {

        allCheques.sort(
                Comparator.comparing(Cheque::getChequeAmount).reversed());

        allCheques.forEach(System.out::println);
    }

    @Override
    public void sortByAmountAscending(List<Cheque> allCheques) {

        Collections.sort(
                allCheques,
                Comparator.comparing(Cheque::getChequeAmount));

        allCheques.forEach(System.out::println);
    }

    @Override
    public void sortByPriorityAndStatus(List<Cheque> allCheques) {

        allCheques.sort(
                Comparator.comparing(Cheque::getPriority)
                          .thenComparing(Cheque::getStatus));

        allCheques.forEach(System.out::println);
    }
}