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
        user.setId(Identity.INSTANCE.generatedIdUser());
        userCollection.addUser(user);
        log.info("Пользователь сохранён, id = {}", user.getId());
        return getUser(user.getId());
    }

    public User updateUser(User newUser) {
        validationUser(newUser);
        User userInCollection = getUser(newUser.getId());
        newUser.getFriends().addAll(userInCollection.getFriends());
        userCollection.updateUser(newUser);
        log.info("Пользователь с id - {} обновлён", newUser.getId());
        return getUser(newUser.getId());
    }

    public User getUser(int id) {
        User user = userCollection.getUser(id);
        if (user == null) {
            log.info("Пользователь с id - {} не найден", id);
            throw new NotFoundException("Пользователь с id - " + id + " не найден");
        }
//        log.info("Получен пользователь с id - {}", id);
        return user;
    }

    public List<User> getUsers() {
        return new ArrayList<>(userCollection.getUsers().values());
    }

    public void removeUser(int id) {
        User user = getUser(id);
        userCollection.removeUser(user.getId());
        log.info("Пользователь id - {} удалён", id);
    }

    public void addFriend(int userId, int friendId) {
        validateId(userId, friendId);
        User user = getUser(userId);
        User friend = getUser(friendId);
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
        log.info("Пользователи с id - {}, id - {} стали друзьями", userId, friendId);
    }

    public List<User> getFriends(int userId) {
        User user = getUser(userId);
        Set<Integer> friendIds = user.getFriends();
        List<User> friends = new ArrayList<>();

        for (int id : friendIds) {
            User userFriend = getUser(id);
            friends.add(userFriend);
        }
        log.info("Список друзей пользователя id - {}", user.getId());
        return friends;
    }

    public void removeFriend(int userId, int friendId) {
        validateId(userId, friendId);
        User user = getUser(userId);
        User friend = getUser(friendId);
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
        log.info("Пользователи с id - {}, id - {} удалены друг у друга из друзей", userId, friendId);
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        validateId(userId, otherId);
        User user = getUser(userId);
        User otherUser = getUser(otherId);
        List<User> commonFriends = new ArrayList<>();

        for (int friendIdByUser : user.getFriends()) {
            if (otherUser.getFriends().contains(friendIdByUser)) {
                commonFriends.add(getUser(friendIdByUser));
            }
        }
        log.info("Список общих друзей пользователей с id - {}, id - {}", user.getId(), otherUser.getId());
        return commonFriends;
    }

    private void validateId(int userId, int otherId) {
        if (userId == otherId) {
            throw new ValidationException("Некорректные данные");
        }
    }

    private void validationUser(User user) {
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
