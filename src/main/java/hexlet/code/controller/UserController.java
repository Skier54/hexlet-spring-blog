package hexlet.code.controller;

import hexlet.code.dto.dtoUser.UserCreateDTO;
import hexlet.code.dto.dtoUser.UserDTO;
import hexlet.code.dto.dtoUser.UserPatchDTO;
import hexlet.code.dto.dtoUser.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDTO> indexUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        var userPage = userRepository.findAll(pageable);

        return userPage.map(userMapper::toDTO);
        //var users = userRepository.findAll();

        //return users.stream().skip((long) (page - 1) * size).limit(size).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO showUser(@PathVariable Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userMapper.toDTO(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userData) {
        //user.setId((long) userIdCounter.getAndIncrement());
        //users.add(user);
        //URI location = URI.create("/api/users/" + user.getId());
        var user = userMapper.toEntity(userData);
        userRepository.save(user);
        var postDTO = userMapper.toDTO(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + postDTO.getId()))
                .body(postDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userData) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        userMapper.toEntityUpdate(userData, user);
        user.setUpdatedAt(LocalDate.from(LocalDateTime.now()));
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable Long id,
                                             @Valid @RequestBody UserPatchDTO dto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        dto.getFirstName().ifPresent(user::setFirstName);
        dto.getLastName().ifPresent(user::setLastName);
        dto.getEmail().ifPresent(user::setEmail);

        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDTO(user));
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
