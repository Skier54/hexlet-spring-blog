package hexlet.code.controller;

import hexlet.code.model.Post;
import hexlet.code.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    //private final List<Post> posts = new ArrayList<>();
    @Autowired
    private PostRepository postRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> indexPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {

        var posts = postRepository.findAll();

        return posts.stream().skip((long) (page - 1) * limit).limit(limit).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post showPost(@PathVariable Long id) {

        return postRepository.findById(id).get();
        //var post = posts.stream()
        //        .filter(p -> p.getSlug().equals(id))
        //        .findFirst();
        //return ResponseEntity.of(post);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post) {

        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@PathVariable Long id, @RequestBody Post data) {
        var maybePost = postRepository.findById(id);
        if (maybePost.isPresent()) {
            var post = maybePost.get();
            post.setTitle(data.getTitle());
            post.setContent(data.getContent());
            post.setPublished(data.isPublished());
            return postRepository.save(post);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyPost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
}
