package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(String name, String email);

    List<User> getAllUsers();

    User getUserById(UUID id);

    List<User> searchUsersByName(String name);
}
