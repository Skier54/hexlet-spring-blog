package hexlet.code.controller;

import hexlet.code.dto.dtoPost.PostDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.PostMapper;
import hexlet.code.model.Post;
import hexlet.code.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PostDTO> indexPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var postPage = postRepository.findByPublishedTrue(pageable);

        return postPage.map(postMapper::toDTO);

        //var posts = postRepository.findAll();
        //return posts.stream().skip((long) (page - 1) * limit).limit(limit).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDTO showPost(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return postMapper.toDTO(post);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody Post post) {
        var savedPost = postRepository.save(post);
        var dto = postMapper.toDTO(post);
        return ResponseEntity
                .created(URI.create("/api/posts/" + savedPost.getId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDTO updatePost(@PathVariable Long id, @Valid @RequestBody Post data) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        post.setPublished(data.isPublished());
        postRepository.save(post);

        return postMapper.toDTO(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyPost(@PathVariable Long id) {
        var post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResourceNotFoundException("Post with ID " + id + " not found");
        }
        postRepository.deleteById(id);
    }
}