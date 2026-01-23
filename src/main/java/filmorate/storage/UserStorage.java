package filmorate.storage;

import filmorate.model.User;

import java.util.List;

public interface UserStorage {

    void addUser(User user);

    void removeUser(int id);

    void updateUser(User user);

    User getUser(int id);

    List<User> getUsers();
}
