package web.api.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Optional;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "Email is mandatory")
    @Email
    String email;

    @NotBlank(message = "Pass is mandatory")
    @Size(min = 8)
    String pass;
    String firstName;
    Optional<String> secondName = Optional.empty();

}
