import com.example.ex02.Book;
import com.example.ex02.BookRepository;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostgresBookTest {
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    @Autowired
    private BookRepository bookRepository;

    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Order(1)
    public void insertGetTest(){
        Book book = new Book();
        book.setName("IT TIME");
        bookRepository.save(book);
        assertThat(bookRepository.findById(1l), equalTo(book));
    }
    @Test
    @Order(2)
    public void getAllTest(){
        assertThat(bookRepository.findAll().size(),equalTo(1));
    }

}
