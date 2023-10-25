package com.todo.controller;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user.getName(), user.getEmail(), user.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        return ResponseEntity.ok(userService.loginUser(user.getEmail(), user.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<User> logoutUser(@RequestBody User user){
        return ResponseEntity.ok(userService.logoutUser(user.getEmail()));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PostMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestBody User user){
        return ResponseEntity.ok(userService.deleteUser(user));
    }

    @PostMapping("/addWorkspace")
    public ResponseEntity<Workspace> addWorkspaceToUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addWorkspaceToUser(user, user.getWorkspaces().get(0)));
    }

    @PostMapping("/removeWorkspace")
    public ResponseEntity<Workspace> removeWorkspaceFromUser(@RequestBody User user){
        return ResponseEntity.ok(userService.removeWorkspaceFromUser(user, user.getWorkspaces().get(0)));
    }

}
