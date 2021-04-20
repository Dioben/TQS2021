package book;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookSearchSteps {
    Library lib;
    List<Book> result;

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime iso8601Date(String year, String month, String day){
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0);
    }

    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void startNewLibrary(final String title, final String author, final LocalDateTime published) {
        lib = new Library();
        Book book = new Book(title, author, published);
        lib.addBook(book);
    }
    @Given(("another book with the title {string}, written by {string}, published in {iso8601Date}"))
    public void addNewBook(final String title, final String author, final LocalDateTime published) {
        Book book = new Book(title, author, published);
        lib.addBook(book);
    }
    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(final LocalDateTime from,final LocalDateTime to) {
        result = lib.findBooks(from, to);
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertEquals(result.size(),booksFound);
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertEquals(result.get(position - 1).getTitle(), title);
    }
}
