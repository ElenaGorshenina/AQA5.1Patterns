package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationCard;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.Registration.city;
import static ru.netology.data.DataGenerator.dateFormation;

public class CardDeliveryPlanTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    void shouldGenerateTestData(){

        RegistrationCard info = DataGenerator
                .Registration
                .generateByCard("ru");

        open("http://localhost:9999");
        $("[placeholder='Город']").setValue(city());
        $("[data-test-id='date'] [type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [type='tel']").setValue(dateFormation(3));
        $("[name='name']").setValue(info.getName());
        $("[name='phone']").setValue(info.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + dateFormation(3)));
        $("[data-test-id='success-notification'] .icon-button__content").click();

        //Перепланирование даты встречи
        $("[data-test-id='date'] [type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [type='tel']").setValue(dateFormation(4));
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__title").shouldHave(exactText("Необходимо подтверждение"));
        $(byText("Перепланировать")).click();
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + dateFormation(4)));

    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}
