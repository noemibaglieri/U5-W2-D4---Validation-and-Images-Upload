package noemibaglieri.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import noemibaglieri.entities.Author;
import noemibaglieri.exceptions.BadRequestException;
import noemibaglieri.exceptions.NotFoundException;
import noemibaglieri.payloads.NewAuthorPayload;
import noemibaglieri.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class AuthorsService {

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private Cloudinary imgUploader;

    public Author save(NewAuthorPayload payload) {

        this.authorsRepository.findByEmail(payload.getEmail()).ifPresent(author -> {
            throw new BadRequestException("This email * " + author.getEmail() + " * is already in use.");
        });

        Author newAuthor = new Author(payload.getFirstName(), payload.getLastName(), payload.getEmail(), payload.getDateOfBirth(), "https://ui-avatars.com/api/?name=" + payload.getFirstName() + "+" + payload.getLastName());
        this.authorsRepository.save(newAuthor);
        log.info("The author * " + newAuthor.getFirstName() + newAuthor.getLastName() + " * was successfully registered to the DB");
        return newAuthor;
    }

    public List<Author> findAll() {
        return this.authorsRepository.findAll();
    }

    public Author findById(long authorId) {
       return this.authorsRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public Author findByIdAndUpdate(long authorId, NewAuthorPayload payload) {
        Author found = this.findById(authorId);
        if(!found.getEmail().equals(payload.getEmail())) {
            this.authorsRepository.findByEmail(payload.getEmail()).ifPresent(author -> {
                throw new BadRequestException("The email * " + author.getEmail() + " * is already in use!");
            });
        }

        found.setFirstName(payload.getFirstName());
        found.setLastName(payload.getLastName());
        found.setEmail(payload.getEmail());
        found.setDateOfBirth(payload.getDateOfBirth());
        found.setAvatar("https://ui-avatars.com/api/?name=" + payload.getFirstName() + "+" + payload.getLastName());

        Author editedAuthor = this.authorsRepository.save(found);

        log.info("The author with id " + found.getId() + " was successfully updated");

        return editedAuthor;
    }

   public void findByIdAndDelete(long authorId) {
        Author found = this.findById(authorId);
        this.authorsRepository.delete(found);
    }

    public String uploadAvatar(MultipartFile file) {
        try {
            String imgUrl = (String) imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            return imgUrl;
        } catch (IOException e) {
            throw new BadRequestException("There was a problem uploading this file.");
        }
    }
}
