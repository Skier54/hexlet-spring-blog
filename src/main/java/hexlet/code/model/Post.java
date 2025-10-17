package hexlet.code.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
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

    @LastModifiedDate
    private LocalDate updatedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)  //не может быть ноль, не изменяемо
    private LocalDate createdAt;

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "user_id", nullable = false)
    //private User user;

//    public void setTitle(String title) {
//        this.title = title;
//        this.slug = generateSlug(title);
//    }
//
//    private String generateSlug(String title) {
//        return title.toLowerCase().replaceAll("\\s+", "-");
//    }
}
