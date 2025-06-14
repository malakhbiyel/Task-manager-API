package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Récupérer la liste de tous les utilisateurs (admin uniquement)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class, type = "array"))),
                    @ApiResponse(responseCode = "403", description = "Accès refusé")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Récupérer un utilisateur par son ID (admin uniquement)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
                    @ApiResponse(responseCode = "403", description = "Accès refusé")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Récupérer les informations de l'utilisateur connecté",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Informations utilisateur récupérées",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userService.getUserByUsername(username)
                .map(user -> ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Supprimer un utilisateur par son ID (admin uniquement)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
                    @ApiResponse(responseCode = "403", description = "Accès refusé")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
