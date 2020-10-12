package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/user/add")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 = userRepository.save(user);

        if(user1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("user/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "user/{id}")
    public Optional<User> getUserById(@PathVariable int id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("User with id "+id+" doesnt exist !");

        return user;
    }

    @PutMapping(value = "user/update/{id}")
    public User updateUserById(@PathVariable int id, @RequestBody User user){
        Optional<User> user1 = userRepository.findById(id);

        if(user1.isPresent()){
            User userToUpdate = user1.get();
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));;
            userToUpdate.setFullname(user.getFullname());
            userToUpdate.setRole(user.getRole());
            userRepository.save(userToUpdate);
            return userToUpdate;
        }
        return null;
    }

    @DeleteMapping(value = "user/delete/{id}")
    public List<User> deleteUserById(@PathVariable int id){
        userRepository.deleteById(id);
        return getAllUser();
    }


    @GetMapping(value = "user/list")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }


/*
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

 */
}
