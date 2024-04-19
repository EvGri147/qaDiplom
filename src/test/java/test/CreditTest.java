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
    @DisplayName("Payment by approved card")
    void shouldPayByApprovedCard() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("456");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Payment with an inactive card")
    void shouldPayByDeclinedCard() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment with an unknown card, valid data")
    void shouldPayUnknownCard() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444443");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment by card with an invalid card number")
    void shouldPayInvalidCardNumber() {
        formPage.buyOnCredit();
        formPage.setCardNumber("444444444444AAAA");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an invalid month number")
    void shouldPayInvalidMonth() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("15");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
    }

    @Test
    @DisplayName("Payment by card with an invalid year number")
    void shouldPayInvalidYear() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("22");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Payment by card with an invalid owner")
    void shouldPayInvalidCardOwner() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria 3456 Петrova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment by card with an invalid CVV")
    void shouldPayInvalidCVV() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("7DD");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card number")
    void shouldPayEmptyCardNumber() {
        formPage.buyOnCredit();
        formPage.setCardNumber("");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card month")
    void shouldPayEmptyCardMonth() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card year")
    void shouldPayEmptyCardYear() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an empty card owner")
    void shouldPayEmptyCardOwner() {
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
    @DisplayName("Payment by card with an empty card CVV")
    void shouldPayEmptyCardCVV() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment using an approved card, checking the database entry")
    void shouldPayByApprovedCardCreditStatusDB() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkCreditStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Payment inactive card, checking the database entry")
    void shouldPayByDeclinedCardInCreditStatusInDB() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("432");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkCreditStatus(Status.DECLINED);
    }
}
