package hexlet.code.dto.dtoUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UserPatchDTO {
    @NotBlank
    @Size(min = 2, max = 30)
    private JsonNullable<String> firstName = JsonNullable.undefined();

    @NotBlank
    @Size(min = 2, max = 30)
    private JsonNullable<String> lastName = JsonNullable.undefined();

    @NotBlank
    @Email
    @Size(min = 5, max = 100)
    private JsonNullable<String> email = JsonNullable.undefined();
}
