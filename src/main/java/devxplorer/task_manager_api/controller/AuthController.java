package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.UserCreateDTO;
import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.security.JwtUtil;
import devxplorer.task_manager_api.service.CustomUserDetailsService;
import devxplorer.task_manager_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Operation(summary = "Authentifier un utilisateur et obtenir un token JWT",
            requestBody = @RequestBody(
                    description = "Données de connexion avec username et password",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Connexion réussie, token JWT retourné"),
                    @ApiResponse(responseCode = "401", description = "Échec de l'authentification")
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCreateDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @Operation(summary = "Créer un nouvel utilisateur (inscription)",
            requestBody = @RequestBody(
                    description = "Données de création d'utilisateur",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCreateDTO dto) {
        UserDTO createdUser = userService.createUser(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
