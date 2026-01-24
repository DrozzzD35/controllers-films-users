package filmorate.service;

import filmorate.exception.NotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.User;
import filmorate.storage.UserStorage;
import filmorate.utils.Identity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
        User user = userCollection.getUser(id);
        if (user == null) {
            log.info("Пользователь с id {} не найден", id);
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        log.info("Получен пользователь, id: {}", id);
        return user;
    }

    public List<User> getUsers() {
        return new ArrayList<>(userCollection.getUsers().values());
    }

    public void removeUser(int id) {
        userCollection.removeUser(id);
        log.info("Пользователь удалён, id пользователя = {}", id);
    }

    public void addFriend(int userId, int friendId) {
        User user = getUser(userId);
        User friend = getUser(userId);
        user.getFriend().add(friend.getId());
        friend.getFriend().add(user.getId());
        log.info("Пользователи с id {}  {} стали друзьями", userId, friendId);
    }

    public List<User> getFriends(int userId) {
        User user = getUser(userId);
        Set<Integer> friendIds = user.getFriend();
        List<User> friends = new ArrayList<>();

        for (int id : friendIds) {
            User userFriend = getUser(id);
            friends.add(userFriend);
        }
        return friends;
    }

    public void removeFriend(int userId, int friendId) {
        User user = getUser(userId);
        User friend = getUser(userId);
        user.getFriend().remove(friend.getId());
        friend.getFriend().remove(user.getId());
        log.info("Пользователи с id {}  {} не друзья", userId, friendId);
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
