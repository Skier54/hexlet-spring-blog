package hexlet.code.controller;

import hexlet.code.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final List<User> users = new ArrayList<>();
    private final AtomicInteger userIdCounter = new AtomicInteger(1);

    @GetMapping
    public ResponseEntity<List<User>> indexUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        var user =  users.stream().skip((long) (page - 1) * limit).limit(limit).toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> showUser(@PathVariable Integer id) {

        return users.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId((long) userIdCounter.getAndIncrement());
        users.add(user);
        URI location = URI.create("/api/users/" + user.getId());
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User data) {
        var maybeUser = users.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> destroyUser(@PathVariable Integer id) {
        boolean removed = users.removeIf(p -> p.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
