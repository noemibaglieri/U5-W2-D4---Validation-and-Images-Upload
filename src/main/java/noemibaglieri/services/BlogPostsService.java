package noemibaglieri.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import noemibaglieri.entities.Author;
import noemibaglieri.entities.BlogPost;
import noemibaglieri.exceptions.BadRequestException;
import noemibaglieri.exceptions.NotFoundException;
import noemibaglieri.payloads.NewBlogPostDTO;
import noemibaglieri.repositories.AuthorsRepository;
import noemibaglieri.repositories.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BlogPostsService {

    @Autowired
    private BlogPostsRepository blogPostsRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private Cloudinary imgUploader;

    public BlogPost save(NewBlogPostDTO payload) {
        Author author;
        Optional<Author> authorResponse = this.authorsRepository.findById(payload.author());
        author = authorResponse.orElseGet(Author::new);
        BlogPost newBlogPost = new BlogPost(payload.category(), payload.title(), payload.content(), payload.readingTime(), "https://picsum.photos/200/300", author);

        this.blogPostsRepository.save(newBlogPost);
        log.info("Your blog post * " + newBlogPost.getTitle() + " * was successfully published");
        return newBlogPost;
    }

    public List<BlogPost> findAll() {
        return this.blogPostsRepository.findAll();
    }

    public BlogPost findById(long blogId) {
        return this.blogPostsRepository.findById(blogId).orElseThrow(() -> new NotFoundException(blogId));
    }

    public BlogPost findByIdAndUpdate(long blogId, NewBlogPostDTO payload) {
        BlogPost found = this.findById(blogId);

        found.setCategory(payload.category());
        found.setTitle(payload.title());
        found.setContent(payload.content());
        found.setReadingTime(payload.readingTime());
        found.setCover("https://picsum.photos/200/300");
        Author author;
        Optional<Author> authorResponse = this.authorsRepository.findById(payload.author());
        author = authorResponse.orElseGet(Author::new);
        found.setAuthor(author);

        BlogPost editedPost = this.blogPostsRepository.save(found);

        log.info("The post with title * " + found.getTitle() + " * was successfully updated");

        return editedPost;
    }

    public void findByIdAndDelete(long blogId) {
        BlogPost found = this.findById(blogId);
        this.blogPostsRepository.delete(found);
    }

    public String uploadCover(MultipartFile file) {
        try {
            return (String) imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("There was a problem uploading this file.");
        }

    }
}
