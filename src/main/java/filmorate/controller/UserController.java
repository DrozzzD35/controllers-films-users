package filmorate.controller;

import filmorate.model.User;
import filmorate.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/user")
    public User addUser(@RequestBody User user) {
        userService.addUser(user);
        return userService.getUser(user.getId());
    }

    @PutMapping("/users/user")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return userService.getUser(user.getId());
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
