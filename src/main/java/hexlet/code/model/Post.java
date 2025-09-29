package hexlet.code.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private String slug;

    public void setTitle(String title) {
        this.title = title;
        this.slug = generateSlug(title);
    }

    private String generateSlug(String title) {
        return title.toLowerCase().replaceAll("\\s+", "-");
    }
}
