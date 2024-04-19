package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBUtils;
import data.FormPage;
import data.Status;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

public class PaymentTest {

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
    @DisplayName("Payment by approved card, valid details")
    void shouldPayByApprovedCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Payment using an unapproved card")
    void shouldPayByDeclinedCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment using an unknown card")
    void shouldPayBynUnkCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444444");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment using a card with an invalid number")
    void shouldPayInvalidNumberCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("444444444444AAAA");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an invalid month")
    void showPayBadMonth() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("15");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();

    }

    @Test
    @DisplayName("Payment by card with invalid year")
    void showPayBadYear() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("22");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Payment by card with an invalid field owner")
    void showPayBadOwner() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria 3456 Петrova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment by card with an invalid CVV field")
    void showPayBadCVC() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("6DD");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment for a tour with an empty card number")
    void showPayWithEmplyCardNumber() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment for a tour with an empty card month")
    void showPayWithEmplyCardMonth() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();

    }

    @Test
    @DisplayName("Payment for a tour with an empty card year")
    void showPayWithEmplyCardYear() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("");
        formPage.setCardOwner("MariaIvanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();

    }

    @Test
    @DisplayName("Payment for a tour with an empty card owner")
    void showPayWithEmptyCardOwner() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageRequiredField();

    }

    @Test
    @DisplayName("Payment for a tour with an empty card owner")
    void showPayWithEmplyCardCVV() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();

    }

    @Test
    @DisplayName(" Payment using an approved card, checking the database entry")
    void showPayAndEntryDB() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkPaymentStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Payment inactive card, checking database entries")
    void shouldNoPayByDeclinedCardStatusInDB() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Maria Petrova");
        formPage.setCardCVV("543");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkPaymentStatus(Status.DECLINED);
    }
}
