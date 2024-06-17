package cloud.client.cloudClient.service;


import cloud.client.cloudClient.model.User;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();

    User addUser(User user);
    void deleteUser(User user);

    User saveUser(@Valid User user);

    Optional<User> findByUsername(String username);
}
