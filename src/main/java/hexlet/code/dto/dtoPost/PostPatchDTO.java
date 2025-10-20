package hexlet.code.dto.dtoPost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PostPatchDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private JsonNullable<String> title = JsonNullable.undefined();

    @NotBlank
    @Size(min = 10)
    private JsonNullable<String> content = JsonNullable.undefined();

    @NotNull
    private JsonNullable<Boolean> published = JsonNullable.undefined();
}
