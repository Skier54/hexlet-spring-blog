package hexlet.code.controller;

import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/users")
public class UserController {
    //генерируем уникальный id
    //private final AtomicInteger userIdCounter = new AtomicInteger(1);
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> indexUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {

        var users = userRepository.findAll();

        return users.stream().skip((long) (page - 1) * limit).limit(limit).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User showUser(@PathVariable Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        //user.setId((long) userIdCounter.getAndIncrement());
        //users.add(user);
        //URI location = URI.create("/api/users/" + user.getId());
        var savedUser = userRepository.save(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + savedUser.getId()))
                .body(savedUser);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User data) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        user.setEmail(data.getEmail());
        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName());
        user.setBirthday(data.getBirthday());

        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyUser(@PathVariable Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
