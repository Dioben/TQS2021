package book;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<>();

    public void addBook(final Book book) {
        store.add(book);
    }

    public List<Book> findBooks(final LocalDateTime from, final LocalDateTime to) {
        Calendar end = Calendar.getInstance();
        end.setTime(Date.from(to.toInstant(ZoneOffset.ofHours(0))));
        end.roll(Calendar.YEAR, 1);

        return store.stream().filter(book -> {
            return Date.from(from.toInstant(ZoneOffset.ofHours(0))).before(Date.from(book.getPublished().toInstant(ZoneOffset.ofHours(0)))) && end.getTime().after(Date.from(book.getPublished().toInstant(ZoneOffset.ofHours(0))));
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }
}
