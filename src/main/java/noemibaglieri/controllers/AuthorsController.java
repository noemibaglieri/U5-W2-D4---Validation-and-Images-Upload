package noemibaglieri.controllers;

import noemibaglieri.entities.Author;
import noemibaglieri.payloads.NewAuthorPayload;
import noemibaglieri.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    private AuthorsService authorsService;

    @GetMapping
    public List<Author> getAuthors() {
        return this.authorsService.findAll();
    }

    @GetMapping("/{authorId}")
    public Author getAuthorById(@PathVariable long authorId) {
        return this.authorsService.findById(authorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody NewAuthorPayload body) {
        System.out.println(body);
        return this.authorsService.save(body);
    }

    @PutMapping("/{authorId}")
    public Author getAuthorByIdAndUpdate(@RequestBody NewAuthorPayload body, @PathVariable long authorId) {
        return this.authorsService.findByIdAndUpdate(authorId, body);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAuthorByIdAndDelete(@PathVariable long authorId) {
        this.authorsService.findByIdAndDelete(authorId);
    }
}
