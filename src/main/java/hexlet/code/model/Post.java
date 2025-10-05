package hexlet.code.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Заголовок поста не может быть пустым")
    @Size(min = 3, max = 50, message = "Длина заголовка должна быть от 3 до 50 символов")
    private String title;

    @NotBlank(message = "Содержание поста не может быть пустым")
    @Size(min = 10, message = "Длина поста должна быть не менее 10 символов")
    private String content;

    @NotNull(message = "Статус публикации должен быть указан")
    private boolean published;

//    public void setTitle(String title) {
//        this.title = title;
//        this.slug = generateSlug(title);
//    }
//
//    private String generateSlug(String title) {
//        return title.toLowerCase().replaceAll("\\s+", "-");
//    }
}
