package com.example.demo.rest;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UserResource {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = GET, path = "users")
    public ResponseEntity<Iterable<User>> getUsers() {
        Iterable<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(method = GET, path = "users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        try{
            User user = userRepository.findOne(id);
            return ResponseEntity.ok(user);
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(method = POST, path = "users")
    public ResponseEntity<Void> createUser(@RequestBody UserDto dto) throws URISyntaxException {
            User newUser = UserMapper.from(dto);
            userRepository.save(newUser);
            return ResponseEntity.created(new URI("/users/"+newUser.getId())).build();
    }

    @RequestMapping(method = PUT, path = "users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        try{
            User userToUpdate =  userRepository.findOne(id);
            userToUpdate.setName(dto.getName());
            userToUpdate.setSurname(dto.getSurname());
            userRepository.save(userToUpdate);
            return ResponseEntity.noContent().build();
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = DELETE, path = "users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userRepository.delete(id);
            return ResponseEntity.noContent().build();
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
