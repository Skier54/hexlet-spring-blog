package hexlet.code.controller;

import hexlet.code.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    private final List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> index(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        var post = posts.stream().skip((long) (page - 1) * limit).limit(limit).toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(post);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> show(@PathVariable String id) {

        return posts.stream()
                .filter(p -> p.getSlug().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        //var post = posts.stream()
        //        .filter(p -> p.getSlug().equals(id))
        //        .findFirst();
        //return ResponseEntity.of(post);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post post) {
        posts.add(post);
        URI location = URI.create("/posts/" + post.getSlug());
        return ResponseEntity.created(location).body(post);

        //return ResponseEntity.status(HttpStatus.CREATED)
        //        .body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post data) {
        var maybePost = posts.stream()
                .filter(p -> p.getSlug().equals(id))
                .findFirst();
        if(maybePost.isPresent()) {
            var post = maybePost.get();
            post.setTitle(data.getTitle());
            post.setAuthor(data.getAuthor());
            post.setContent(data.getContent());
            post.setCreatedAt(data.getCreatedAt());
            return ResponseEntity.ok(post);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        boolean removed = posts.removeIf(p -> p.getSlug().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
