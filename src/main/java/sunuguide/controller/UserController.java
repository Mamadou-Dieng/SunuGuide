package sunuguide.controller;

import sunuguide.dto.UserResponseDTO;
import sunuguide.dto.UserUpdateDTO;
import sunuguide.model.User;
import sunuguide.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users/{userId} : Récupère le profil.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserProfile(@PathVariable Long userId) {
        try {
            User user = userService.findById(userId);
            return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT /api/users/{userId} : Met à jour le profil (langue et préférences).
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateProfile(@PathVariable Long userId,
                                                         @RequestBody UserUpdateDTO updateDTO) {
        try {
            User userToUpdate = userService.findById(userId);

            userToUpdate.setPreferredLanguage(updateDTO.getPreferredLanguage());
            userToUpdate.setPreferences(updateDTO.getPreferences());

            User updatedUser = userService.save(userToUpdate);

            return ResponseEntity.ok(UserResponseDTO.fromEntity(updatedUser));

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE /api/users/{userId} : Supprime le compte utilisateur.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        // La méthode de service gère la recherche et la suppression
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }
}