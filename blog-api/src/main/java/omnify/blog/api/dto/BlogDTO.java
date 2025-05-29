package omnify.blog.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private String authorEmail;
}

