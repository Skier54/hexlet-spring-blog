package hexlet.code.mapper;

import hexlet.code.dto.dtoPost.PostCreateDTO;
import hexlet.code.dto.dtoPost.PostDTO;
import hexlet.code.dto.dtoPost.PostUpdateDTO;
import hexlet.code.model.Post;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PostMapper {

    PostDTO toDTO(Post post);

    Post toEntity(PostCreateDTO postData);

    void toEntityUpdate(PostUpdateDTO postDTO, @MappingTarget Post post);
}
//@Component
//public class PostMapper {
//
//    public PostDTO toDTO(Post post) {
//        var dto = new PostDTO();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        dto.setPublished(post.isPublished());
//        dto.setCreatedAt(post.getCreatedAt());
//        dto.setUpdatedAt(post.getUpdatedAt());
//        //dto.setUserId(post.getUser().getId());
//        return dto;
//    }
//
//    public Post toEntity(PostCreateDTO postDTO) {
//        var post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setContent(postDTO.getContent());
//        post.setPublished(postDTO.getPublished());
//        return post;
//    }
//
//    public Post toEntityUpdate(PostUpdateDTO postDTO, Post post) {
//        //post.setTitle(postDTO.getTitle());
//        post.setContent(postDTO.getContent());
//        post.setPublished(postDTO.getPublished());
//        post.setUpdatedAt(LocalDate.from(LocalDateTime.now()));
//        return post;
//    }
//}
