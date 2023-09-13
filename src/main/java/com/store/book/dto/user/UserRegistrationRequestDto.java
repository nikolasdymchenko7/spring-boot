package com.store.book.dto.user;

import com.store.book.lib.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(field = "password",
                fieldMatch = "repeatPassword",
                message = "Passwords doesn't match")
public class UserRegistrationRequestDto {
    @NotBlank
    @Size(min = 6, max = 100)
    private String email;
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
    @NotBlank
    @Size(min = 6, max = 100)
    private String repeatPassword;
    @NotBlank
    @Size(min = 2, max = 60)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 100)
    private String lastName;
    private String shippingAddress;
}
