package hexlet.code.mapper;

import hexlet.code.dto.dtoPost.PostDTO;
import hexlet.code.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO toDTO(Post post) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPublished(post.isPublished());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        //dto.setUserId(post.getUser().getId());
        return dto;
    }
}
