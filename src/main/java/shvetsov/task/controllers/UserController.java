package shvetsov.task.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvetsov.task.dto.UserDTO;
import shvetsov.task.services.UserService;

/**
 * User controller.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "API для управления пользователями")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Reg user response entity.
     *
     * @param userDTO the user dto
     * @return the response entity
     */
    @Operation(summary = "Зарегистрировать пользователя", description = "Регистрирует пользователя")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO){
        return userService.registrationUser(userDTO.getEmail(), userDTO.getPassword());
    }

    /**
     * Del user response entity.
     *
     * @param email the email
     * @return the response entity
     */
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя")
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String email){
        return userService.delUser(email);
    }

    /**
     * Authenticate user response entity.
     *
     * @param userDTO the user dto
     * @return the response entity
     */
    @Operation(summary = "Авторизовать и аутентифицировать пользователя", description = "Авторизует и аутентифицирует пользователя")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.authenticateUser(userDTO.getEmail(), userDTO.getPassword());
    }
}
