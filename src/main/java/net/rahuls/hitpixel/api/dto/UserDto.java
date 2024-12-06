package net.rahuls.hitpixel.api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private Long id;

    @NotNull(message = "Username is required.")
    @Size(min = 5, max = 30, message = "Username must be at least 5 and at most 30 characters long")
    private String username;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Full Name must be at least 2 and at most 50 characters long")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Size(min = 6, max = 30, message = "Email must be at least 6 and at most 30 characters long")
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain one UPPERCASE, one lowercase, one number and one special character")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public UserDto() {
        this(null);
    }

    public UserDto(Long id) {
        this.id = id;
    }
}
