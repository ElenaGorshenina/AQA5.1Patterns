package ru.netology;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryPlanTest {
    private Faker faker;

    String dateFormation(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUpAll() {
        faker =new Faker(new Locale("ru"));
    }

    @Test
    void shouldGenerateTestData(){

        RegistrationCard info = DataGenerator
                .Registration
                .generateByCard("ru");
        System.out.println(info.getCity() + "\n" + info.getMeetingDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n" + info.getName() + "\n" + info.getPhone());

        String dateMeeting = info.getMeetingDate().format((DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        open("http://localhost:9999");
        $("[placeholder='Город']").setValue(info.getCity());
        $("[data-test-id='date'] [type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [type='tel']").setValue(dateMeeting);
        $("[name='name']").setValue(info.getName());
        $("[name='phone']").setValue(info.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + dateMeeting));
        $("[data-test-id='success-notification'] .icon-button__content").click();

        //Перепланирование даты встречи
        String dateMeeting2 = info.getMeetingDate().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [type='tel']").setValue(dateMeeting2);
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__title").shouldHave(exactText("Необходимо подтверждение"));
        $(byText("Перепланировать")).click();
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + dateMeeting2));

    }
}
