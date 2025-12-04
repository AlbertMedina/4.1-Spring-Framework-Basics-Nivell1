package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private static final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User userRequest) {
        User newUser = new User(UUID.randomUUID(), userRequest.name(), userRequest.email());
        users.add(newUser);
        return newUser;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable UUID id) {
        return users.stream().filter(user -> user.id().equals(id)).findFirst().orElseThrow(() -> new UserNotFoundException("User " + id + " not found"));
    }
}
