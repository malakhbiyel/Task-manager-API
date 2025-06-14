package devxplorer.task_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informations d'un utilisateur existant")
public class UserDTO {

    @Schema(description = "Identifiant unique de l'utilisateur", example = "5")
    private Long id;

    @Schema(description = "Nom d'utilisateur", example = "johndoe")
    private String username;

    @Schema(description = "Adresse email", example = "john@example.com")
    private String email;

    public UserDTO() {}

    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}