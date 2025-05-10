package com.catfood.store.controllers;

import com.catfood.store.dtos.RegisterUserRequest;
import com.catfood.store.dtos.UpdateUserRequest;
import com.catfood.store.dtos.UserDto;
import com.catfood.store.mappers.UserMapper;
import com.catfood.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            //@RequestHeader(required = false, name = "x-auth-token") String authToken,
            @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy
    ) {
        //System.out.println(authToken);

        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
//    public UserDto createUser(@RequestBody UserDto data) {
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {
//        return data;
        var user = userMapper.toEntity(request);
        //System.out.println(user);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        //return null;
        //return userDto;
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
//    public UserDto updateUser(
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
//            UserDto) {}
            @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
//            return null;
            return ResponseEntity.notFound().build();
        }

//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//
//        userRepository.save(user);
//
//        return ResponseEntity.ok(userMapper.toDto(user));

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
