package hexlet.code.dto.dtoPost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    @NotBlank
    @Size(min = 10)
    private String content;

    @NotNull
    private Boolean published;
}
