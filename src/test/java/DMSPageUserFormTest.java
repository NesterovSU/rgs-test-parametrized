import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER;

/**
 * @author Sergey Nesterov
 */
public class DMSPageUserFormTest extends DMSBase {

    //ФИО - телефон - емайл - адрес
    static Stream<Arguments> userData() {
        return  Stream.of(
                Arguments.of("Иванов Иван Иванович","9800000011","qwertyqwerty","г Москва, ул Арбат, д 4 стр 1, кв 1"),
                Arguments.of("Сидоров Сидор Сидорович","9800000021","zxcvbzxcvb","г Владивосток, ул Ульяновская, д 2"),
                Arguments.of("Петров Пётр Петрович","9800000022","asdfgasdfg","г Ижевск, ул Удмуртская, д 265 к 1, кв 1")
        );
    }

        @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
        @MethodSource("userData")
        public void testEmailErrorMessage(String name, String telephone, String email, String address) {
            DMSPage
                    .clickForCompanies()
                    .clickHealth()
                    .clickDMS();

            Assertions.assertTrue(DMSPage.isTitleOnPage(), "Отсутствует заголовок на странице");

            DMSPage
                    .agreePolicy()
                    .typeUserName(name)
                    .typeUserTelephone(telephone)
                    .typeUserEmail(email)
                    .typeUserAddress(address);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(name, DMSPage.getUserNameForm(), "Не заполнена форма ФИО"),
                    () -> Assertions.assertEquals(email, DMSPage.getUserEmailForm(), "Не заполнена форма емайл"),
                    () -> Assertions.assertEquals(telephone, DMSPage.getUserTelephoneForm(), "Не заполнена форма телефона"),
                    () -> Assertions.assertEquals(address, DMSPage.getUserAddressForm(), "Не заполнена форма адреса"),
                    () -> Assertions.assertTrue(DMSPage.getPolicyCheckBox(), "Чекбокс политики не выбран")
            );

            DMSPage.submit();
            Assertions.assertTrue(DMSPage.isEmailError(), "Сообщение об ошибке в форме емайл не выведено");

            // Чтобы убедиться в результате
            try {
                sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}
