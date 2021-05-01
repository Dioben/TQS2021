package book;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Book {
    private final String title;
    private final String author;
    private final LocalDateTime published;
    public Book(String t, String a, LocalDateTime z){title=t;author=a; published=z;}
}
