package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
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
    }
}

