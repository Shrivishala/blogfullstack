package omnify.blog.api.service;



import omnify.blog.api.model.User;
import omnify.blog.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = encoder;
    }

    public User registerUser(String email, String rawPassword) throws Exception {
        if (userRepo.existsByEmail(email)) {
            throw new Exception("Email already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepo.save(user);
    }
}
