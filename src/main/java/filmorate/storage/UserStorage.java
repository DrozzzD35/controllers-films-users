package filmorate.storage;

import filmorate.model.User;

import java.util.Map;

public interface UserStorage {

    void addUser(User user);

    void removeUser(int id);

    void updateUser(User user);

    User getUser(int id);

    Map<Integer, User> getUsers();
}
