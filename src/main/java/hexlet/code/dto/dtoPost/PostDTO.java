package hexlet.code.dto.dtoPost;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private boolean published;
    private LocalDate updatedAt;
    private LocalDate createdAt;
    //private Long userId;
}
