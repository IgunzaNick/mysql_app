package com.flutter.flutteruserapp.controller;

import com.flutter.flutteruserapp.model.Users;
import com.flutter.flutteruserapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody Users user){
        Users savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users user){
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    userRepository.save(existingUser);
                    return ResponseEntity.ok(existingUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return userRepository.findById(id)
                .map(existingUser -> {
                    userRepository.delete(existingUser);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
