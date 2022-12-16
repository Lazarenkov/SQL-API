package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.RequestHelper;
import ru.netology.data.SQLHelper;

import static ru.netology.data.RequestHelper.token;

public class TransferTest {


    @AfterAll
    static void cleanDB() {
        SQLHelper.cleanDB();
    }



    @Test
    void createLogin() {
        RequestHelper.sendAuthRequest();
        System.out.println(token);
    }

    @Test
    @DisplayName("Positive Test: Transfer From first to second card valid amount")
    void shouldBeCorrectTransferFrom1to2() {
        String fromCard = DataHelper.getSecondCardNumber();
        String toCard = DataHelper.getFirstCardNumber();

        int value = DataHelper.getValidTransferAmount(fromCard);
        int fromCardBalanceProper = SQLHelper.getCardBalance(fromCard);
        int toCardBalanceProper = SQLHelper.getCardBalance(toCard);

        RequestHelper.sendAuthRequest();
        RequestHelper.sendTransferRequest(fromCard, toCard, value / 100);

        int fromCardBalanceExpected = fromCardBalanceProper - (value);
        int fromCardBalanceActual = SQLHelper.getCardBalance(fromCard);
        int toCardBalanceExpected = toCardBalanceProper + (value);
        int toCardBalanceActual = SQLHelper.getCardBalance(toCard);

        Assertions.assertEquals(fromCardBalanceExpected, fromCardBalanceActual);
        Assertions.assertEquals(toCardBalanceExpected, toCardBalanceActual);
    }

}
