package filmorate.service;

import filmorate.exception.NotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.User;
import filmorate.utils.Identity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {
    private final Map<Integer, User> userCollection = new HashMap<>();

    public User addUser(User user) {
        validationUser(user);
        user.setId(Identity.INSTANCE.generatedIdUser());
        userCollection.put(user.getId(), user);
        log.info("Пользователь сохранён, id = {}", user.getId());
        return getUser(user.getId());
    }

    public User updateUser(User newUser) {
        if (!userCollection.containsKey(newUser.getId())) {
            throw new NotFoundException("Пользователь не найден");
        }
        validationUser(newUser);
        log.info("Пользователь обновлён, id = {}", newUser.getId());
        userCollection.put(newUser.getId(), newUser);
        return getUser(newUser.getId());
    }

    public List<User> getUsers() {
        return new ArrayList<>(userCollection.values());
    }

    public User getUser(int id) {
        return userCollection.get(id);
    }

    private void validationUser(User user) {
        if (user == null) {
            throw new ValidationException("Необходим пользователь");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Ошибка написания почты");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidationException("Ошибка написания логина");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Ошибка даты рождения пользователя");
        }
    }
}
