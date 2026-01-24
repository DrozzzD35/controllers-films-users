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
        userCollection.remove(id);
    }

    @Override
    public void updateUser(User user) {
        userCollection.put(user.getId(), user);
    }

    @Override
    public User getUser(int id) {
        return userCollection.get(id);
    }

    @Override
    public Map<Integer, User> getUsers() {
        return new HashMap<>(userCollection);
    }
}
