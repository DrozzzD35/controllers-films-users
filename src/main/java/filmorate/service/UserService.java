package filmorate.service;

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
        if (!validationUser(user)) {
            log.error("Ошибка валидации при добавлении пользователя {}", user.getName());
            throw new ValidationException("Данные пользователя не соответствуют требования");
        }
        log.info("Пользователь сохранён, id = {}", user.getId());
        user.setId(Identity.INSTANCE.generatedIdUser());
        userCollection.put(user.getId(), user);
        return getUser(user.getId());
    }

    public User updateUser(User newUser) {
        if (!userCollection.containsKey(newUser.getId())) {
            log.error("Ошибка валидации при обновлении пользователя {}", newUser.getName());
            throw new RuntimeException("Пользователь не найден");
        }
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

    private boolean validationUser(User user) {
        if (user == null) return false;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) return false;
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) return false;
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
        return user.getBirthday() != null && !user.getBirthday().isAfter(LocalDate.now());
    }
}
