package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.repository.CoachRepository;
import cloud.client.cloudClient.repository.UserRepository;
import cloud.client.cloudClient.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User saveUser(@Valid User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUser(Long id) {
        return userRepository.findUserById(id);
    }
}
