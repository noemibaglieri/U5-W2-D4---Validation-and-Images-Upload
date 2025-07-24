package noemibaglieri.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewAuthorDTO(
        @NotEmpty(message = "Enter First Name.")
        @Size(min = 2, max = 20, message = "First Name should be 2 to 40 characters long")
        String firstName,
        @NotEmpty(message = "Enter Last Name.")
        @Size(min = 2, max = 20, message = "Last Name should be 2 to 40 characters long")
        String lastName,
        @NotEmpty(message = "Enter email address.")
        @Email(message = "This email is not formatted correctly")
        String email,
        @NotEmpty(message = "Date of birth is mandatory. Enter your birthdate.")
        String dateOfBirth) {
}
