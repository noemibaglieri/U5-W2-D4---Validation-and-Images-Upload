package noemibaglieri.controllers;

import noemibaglieri.entities.Author;
import noemibaglieri.exceptions.ValidationException;
import noemibaglieri.payloads.NewAuthorDTO;
import noemibaglieri.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Author createAuthor(@RequestBody @Validated NewAuthorDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            return this.authorsService.save(body);
        }
    }

    @PutMapping("/{authorId}")
    public Author getAuthorByIdAndUpdate(@RequestBody @Validated NewAuthorDTO body, BindingResult validationResult, @PathVariable long authorId) {
        if(validationResult.hasErrors()) {
            throw new ValidationException((validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList()));
        } else {
            return this.authorsService.findByIdAndUpdate(authorId, body);
        }
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAuthorByIdAndDelete(@PathVariable long authorId) {
        this.authorsService.findByIdAndDelete(authorId);
    }

    @PatchMapping("/{userId}/avatar")
    public String uploadImage(@RequestParam("avatar") MultipartFile file) {
        return this.authorsService.uploadAvatar(file);
    }
}
