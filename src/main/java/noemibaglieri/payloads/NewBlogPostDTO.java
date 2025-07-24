package noemibaglieri.payloads;

public record NewBlogPostDTO(String category, String title, String content, int readingTime, long author) {
}
