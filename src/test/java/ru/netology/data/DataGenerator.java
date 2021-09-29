package ru.netology.data;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker =new Faker(new Locale("ru"));
    }

    public static String dateFormation(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationCard generateByCard(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationCard(
                    faker.address().cityName(),
                    LocalDate.now().plusDays(3),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }

        public static String city() {
            Random random = new Random();
            int rand = random.nextInt(10);
            String citys[] = {"Москва", "Санкт-Петербург", "Оренбург", "Самара", "Чита", "Казань", "Новосибирск", "Нижний Новгород", "Волгоград", "Омск"};
            return citys[rand];
        }
    }
}

