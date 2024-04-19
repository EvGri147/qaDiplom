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
    @DisplayName("Payment by approved card, regular purchase, valid details")
    void shouldPayByApprovedCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Payment using an unapproved card, regular purchase with valid data")
    void shouldPayByDeclinedCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment using an unknown card, regular purchase with valid data")
    void shouldPayBynUnkCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444444");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment using a card with an invalid number, regular purchase")
    void shouldPayInvalidNumberCard() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("444444444444AAAA");
        formPage.setCardMonth("04");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment by card with an invalid month number, regular purchase")
    void showPayBadMonth() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("15");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();

    }

    @Test
    @DisplayName("Payment by card with invalid year number, approved card")
    void showPayBadYearNumber() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("22");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Payment by card with an invalid field owner, approved card")
    void showPayBadOwner() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina 3456 Петrova");
        formPage.setCardCVV("345");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Payment by card with an invalid CVV field, approved card")
    void showPayBadCVC() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("6DD");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment for a tour with an empty card number, regular purchase")
    void showPayWithEmplyCardNumber() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Payment for a tour with an empty card month, regular purchase")
    void showPayWithEmplyCardMonth() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();

    }

    @Test
    @DisplayName("Payment for a tour with an empty card year, regular purchase")
    void showPayWithEmplyCardYear() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();

    }

    @Test
    @DisplayName("Payment for a tour with an empty card owner, regular purchase")
    void showPayWithEmplyCardOwner() {
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
    @DisplayName("Payment for a tour with an empty card owner, regular purchase")
    void showPayWithEmplyCardCVV() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("25");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();

    }

    @Test
    @DisplayName(" Payment using an approved card, regular purchase, valid data, checking the database entry")
    void showPayAndEntryDB() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Ivanova");
        formPage.setCardCVV("654");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkPaymentStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Payment inactive card, regular purchase, valid data, checking database entries")
    void shouldNoPayByDeclinedCardStatusInDB() {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("26");
        formPage.setCardOwner("Irina Petrova");
        formPage.setCardCVV("543");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkPaymentStatus(Status.DECLINED);
    }
}
