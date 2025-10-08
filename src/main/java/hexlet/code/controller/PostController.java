package hexlet.code.controller;

import hexlet.code.exception.ResourceNotFoundException;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Post> indexPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findByPublishedTrue(pageable);

        //var posts = postRepository.findAll();
        //return posts.stream().skip((long) (page - 1) * limit).limit(limit).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post showPost(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return post;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        var savedPost = postRepository.save(post);
        return ResponseEntity
                .created(URI.create("/api/posts/" + savedPost.getId()))
                .body(savedPost);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@PathVariable Long id, @Valid @RequestBody Post data) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        post.setPublished(data.isPublished());

        return postRepository.save(post);
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