package shvetsov.task.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvetsov.task.dto.UserDTO;
import shvetsov.task.services.UserService;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "API для управления пользователями")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Зарегистрировать пользователя", description = "Регистрирует пользователя")
    @PostMapping("/register")
    public ResponseEntity<?> regUser(@RequestBody UserDTO userDTO){
        return userService.registrationUser(userDTO.getEmail(), userDTO.getPassword());
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя")
    @PutMapping("/delete-user")
    public ResponseEntity<?> delUser(@RequestParam String email){
        return userService.delUser(email);
    }

    @Operation(summary = "Авторизовать и аутентифицировать пользователя", description = "Удаляет пользователя")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDTO) {
        return userService.authenticateUser(userDTO.getEmail(), userDTO.getPassword());
    }
}
