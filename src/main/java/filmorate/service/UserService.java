package filmorate.service;

import filmorate.exception.NotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.User;
import filmorate.storage.UserStorage;
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
    private final UserStorage userCollection;

    public UserService(UserStorage userCollection) {
        this.userCollection = userCollection;
    }

    public User addUser(User user) {
        validationUser(user);
        userCollection.addUser(user);
        user.setId(Identity.INSTANCE.generatedIdUser());
        log.info("Пользователь сохранён, id = {}", user.getId());
        return getUser(user.getId());
    }

    public User updateUser(User newUser) {
        validationUser(newUser);
        userCollection.updateUser(newUser);
        log.info("Пользователь обновлён, id = {}", newUser.getId());
        return getUser(newUser.getId());
    }

    public User getUser(int id) {
        return userCollection.getUser(id);
    }

    public List<User> getUsers() {
        return userCollection.getUsers();
    }

    public void removeUser(int id){
        userCollection.removeUser(id);
        log.info("Пользователь удалён, id пользователя = {}", id);
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
