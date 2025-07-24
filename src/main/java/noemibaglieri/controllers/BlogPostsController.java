package noemibaglieri.controllers;

import noemibaglieri.entities.BlogPost;
import noemibaglieri.payloads.NewBlogPostPayload;
import noemibaglieri.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogposts")
public class BlogPostsController {
    @Autowired
    private BlogPostsService blogPostsService;

    @GetMapping
    public List<BlogPost> getPosts() {
        return this.blogPostsService.findAll();
    }

    @GetMapping("/{postId}")
    public BlogPost getPostById(@PathVariable long postId) {
        return this.blogPostsService.findById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost createPost(@RequestBody NewBlogPostPayload body) {
        return this.blogPostsService.save(body);
    }

    @PutMapping("/{postId}")
    public BlogPost getAuthorByIdAndUpdate(@RequestBody NewBlogPostPayload body, @PathVariable long blogId) {
        return this.blogPostsService.findByIdAndUpdate(blogId, body);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getPostByIdAndDelete(@PathVariable long postId) {
        this.blogPostsService.findByIdAndDelete(postId);
    }
}
