package filmorate.controller;

import filmorate.model.User;
import filmorate.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return userService.getUser(user.getId());
    }

    @PutMapping("/users/user")
    public User updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user);
        return userService.getUser(user.getId());
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
