package noemibaglieri.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewBlogPostPayload {

    private String category;
    private String title;
    private String content;
    private int readingTime;
    private long author;

    @Override
    public String toString() {
        return "categoryType='" + category + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", readingTime=" + readingTime + " min" +
                ", author=" + author;
    }

}
