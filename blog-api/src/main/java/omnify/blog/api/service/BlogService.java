package omnify.blog.api.service;

import omnify.blog.api.model.Blog;
import omnify.blog.api.model.User;
import omnify.blog.api.repository.BlogRepository;
import omnify.blog.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepo;
    private final UserRepository userRepo;

    public BlogService(BlogRepository blogRepo, UserRepository userRepo) {
        this.blogRepo = blogRepo;
        this.userRepo = userRepo;
    }

    public Blog createBlog(String title, String content, String userEmail) throws Exception {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setAuthor(user);
        return blogRepo.save(blog);
    }

    public Optional<Blog> getBlog(Long id) {
        return blogRepo.findById(id);
    }

    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    public Blog updateBlog(Long id, String title, String content, String userEmail) throws Exception {
        Blog blog = blogRepo.findById(id).orElseThrow(() -> new Exception("Blog not found"));

        if (!blog.getAuthor().getEmail().equals(userEmail)) {
            throw new Exception("Unauthorized");
        }

        blog.setTitle(title);
        blog.setContent(content);
        return blogRepo.save(blog);
    }

    public void deleteBlog(Long id, String userEmail) throws Exception {
        Blog blog = blogRepo.findById(id).orElseThrow(() -> new Exception("Blog not found"));

        if (!blog.getAuthor().getEmail().equals(userEmail)) {
            throw new Exception("Unauthorized");
        }

        blogRepo.delete(blog);
    }
}
