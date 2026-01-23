package filmorate.storage;

import filmorate.exception.NotFoundException;
import filmorate.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> userCollection = new HashMap<>();

    @Override
    public void addUser(User user) {
        userCollection.put(user.getId(), user);
    }

    @Override
    public void removeUser(int id) {
        if (!userCollection.containsKey(id)) {
            System.out.println("Пользователь с данным идентификатором не найден");
        }
        userCollection.remove(id);
    }

    @Override
    public void updateUser(User user) {
        if (!userCollection.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с данным идентификатором не найден");
        }
        userCollection.put(user.getId(), user);
    }

    @Override
    public User getUser(int id) {
        if (!userCollection.containsKey(id)) {
            System.out.println("Пользователь с данным идентификатором не найден");
        }
        return userCollection.get(id);
    }

    @Override
    public List<User> getUsers() {
        return List.of((User) userCollection.values());
    }
}
