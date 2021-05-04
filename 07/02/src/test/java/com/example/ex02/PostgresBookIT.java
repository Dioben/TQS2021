package com.example.ex02;

import com.example.ex02.Book;
import com.example.ex02.BookRepository;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostgresBookIT {

    @Autowired  BookRepository bookRepository; //this is null, I have literally no clue why

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");



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
        book.setTitle("IT TIME");
        bookRepository.save(book);
        assertThat(bookRepository.findById(2L), equalTo(book));
    }
    @Test
    @Order(2)
    public void getAllTest(){
        assertThat(bookRepository.findAll().size(),equalTo(2));
    }

}
