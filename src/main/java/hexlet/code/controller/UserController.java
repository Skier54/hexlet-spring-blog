package hexlet.code.controller;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
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
    //private final List<User> users = new ArrayList<>();
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
        return userRepository.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        //user.setId((long) userIdCounter.getAndIncrement());
        //users.add(user);
        //URI location = URI.create("/api/users/" + user.getId());
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long id, @RequestBody User data) {
        var maybeUser = userRepository.findById(id);
        if(maybeUser.isPresent()) {
            var user = maybeUser.get();
            user.setEmail(data.getEmail());
            user.setFirstName(data.getFirstName());
            user.setLastName(data.getLastName());
            user.setBirthday(data.getBirthday());

            return userRepository.save(user);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }
}
