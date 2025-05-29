package omnify.blog.api.controller;



import omnify.blog.api.dto.BlogDTO;
import omnify.blog.api.model.Blog;
import omnify.blog.api.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public List<BlogDTO> getAllBlogs() {
        return blogService.getAllBlogs().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlog(@PathVariable Long id) {
        return blogService.getBlog(id)
                .map(blog -> ResponseEntity.ok(toDTO(blog)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogDTO blogDTO,
                                        @AuthenticationPrincipal UserDetails user) {
        try {
            Blog blog = blogService.createBlog(blogDTO.getTitle(), blogDTO.getContent(), user.getUsername());
            return ResponseEntity.ok(toDTO(blog));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id,
                                        @Valid @RequestBody BlogDTO blogDTO,
                                        @AuthenticationPrincipal UserDetails user) {
        try {
            Blog blog = blogService.updateBlog(id, blogDTO.getTitle(), blogDTO.getContent(), user.getUsername());
            return ResponseEntity.ok(toDTO(blog));
        } catch (Exception e) {
            if (e.getMessage().equals("Unauthorized"))
                return ResponseEntity.status(403).body(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetails user) {
        try {
            blogService.deleteBlog(id, user.getUsername());
            return ResponseEntity.ok("Blog deleted");
        } catch (Exception e) {
            if (e.getMessage().equals("Unauthorized"))
                return ResponseEntity.status(403).body(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private BlogDTO toDTO(Blog blog) {
        BlogDTO dto = new BlogDTO();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setContent(blog.getContent());
        dto.setAuthorEmail(blog.getAuthor().getEmail());
        return dto;
    }
}
