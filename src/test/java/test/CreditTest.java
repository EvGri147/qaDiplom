package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBUtils;
import data.FormPage;
import data.Status;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

public class CreditTest {
    private FormPage formPage;

    @BeforeEach
    void setUpPage() {
        formPage = new FormPage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearAll() {
        DBUtils.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("Payment by approved card, purchase on credit, valid data")
    void shouldPayByApprovedCardCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("456");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Payment with an inactive card, purchase on credit, valid data")
    void shouldPayByDeclinedCardCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment with an unknown card, purchase on credit, valid data")
    void shouldPayUnknownCardCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444443");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment by card with an invalid card number, purchase on credit")
    void shouldPayInvalidCardNumberCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("444444444444AAAA");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an invalid month number, purchase on credit")
    void shouldPayInvalidMonthCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("15");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
    }

    @Test
    @DisplayName("Payment by card with an invalid year number, purchase on credit")
    void shouldPayInvalidYearCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("22");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Payment by card with an invalid owner, purchase on credit")
    void shouldPayInvalidCardOwnerCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina 3456 Петrova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment by card with an invalid CVV, purchase on credit")
    void shouldPayInvalidCVVCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("6DD");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card number, purchase on credit")
    void shouldPayEmptyCardNumberCredit() {
        formPage.buyOnCredit();
        formPage.setCardNumber("");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card month, purchase on credit")
    void shouldPayEmptyCardNumberMonth() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card year, purchase on credit")
    void shouldPayEmptyCardNumberYear() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card owner, purchase on credit")
    void shouldPayEmptyCardNumberOwner() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageRequiredField();
    }

    @Test
    @DisplayName("Payment by card with an empty card CVV, purchase on credit")
    void shouldPayEmptyCardNumberCVV() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment using an approved card, purchase on credit, valid data, checking the database entry")
    void shouldPayByApprovedCardCreditStatusDB() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkCreditStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Payment inactive card, purchase on credit, valid data, checking the database entry")
    void shouldPayByDeclinedCardInCreditStatusInDB() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("432");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkCreditStatus(Status.DECLINED);
    }
}
