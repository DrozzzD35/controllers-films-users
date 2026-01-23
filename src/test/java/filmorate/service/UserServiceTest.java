package filmorate.service;

import filmorate.exception.ValidationException;
import filmorate.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UserServiceTest {
    private UserService userService;
    private User user;


    @BeforeEach
    void setUp() {
//        this.userService = new UserService();

        user = new User();
        user.setEmail("123@g.ru");
        user.setLogin("lex");
        user.setName("John");
        user.setBirthday(LocalDate.of(1990, 4, 16));
    }

    @Test
    void name() {
        Assertions.assertNotNull(user);

        user.setLogin("");
        user.setName("");
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setName(" ");
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setLogin("Alex");
        user.setName("Ivan Ivanov");
        Assertions.assertEquals("Ivan Ivanov", user.getName());

        user.setName("Alex");
        Assertions.assertEquals("Alex", user.getName());

        user.setLogin("Alex");
        user.setName("");
        userService.addUser(user);
        Assertions.assertEquals("Alex", user.getName());

    }

    @Test
    void email() {
        user.setEmail(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setEmail("123.ru");
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setEmail("");
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setEmail(" ");
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setEmail("123@ya.ru");
        Assertions.assertEquals("123@ya.ru", user.getEmail());
    }

    @Test
    void login() {
        user.setLogin(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setLogin("");
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setLogin(" ");
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setLogin("Petr Petrov");
        Assertions.assertEquals("Petr Petrov", user.getLogin());

        user.setLogin("Ivan");
        Assertions.assertEquals("Ivan", user.getLogin());

    }

    @Test
    void birthday() {
        user.setBirthday(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setBirthday(LocalDate.of(3000, 12, 6));
        Assertions.assertThrows(ValidationException.class, () -> {
            userService.addUser(user);
        });

        user.setBirthday(LocalDate.of(2000, 12, 6));
        Assertions.assertEquals(LocalDate.of(2000, 12, 6),
                user.getBirthday());

    }

}
