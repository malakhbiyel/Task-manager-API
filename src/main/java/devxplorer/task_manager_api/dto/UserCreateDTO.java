package devxplorer.task_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Données pour la création d'un utilisateur")
public class UserCreateDTO {

    @Schema(description = "Nom d'utilisateur unique", example = "johndoe")
    private String username;

    @Schema(description = "Email de l'utilisateur", example = "john@example.com")
    private String email;

    @Schema(description = "Mot de passe", example = "secret123")
    private String password;

    @Schema(description = "Rôle de l'utilisateur (e.g., USER, ADMIN)", example = "USER")
    private String role;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Add these:
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
