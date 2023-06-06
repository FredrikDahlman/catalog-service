package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        Book book = new Book("1234567890", "Title", "Author", 9.90);
        JsonContent<Book> write = json.write(book);
        Assertions.assertThat(write).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        Assertions.assertThat(write).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        Assertions.assertThat(write).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        Assertions.assertThat(write).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                  {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90
                }
                  """;
        Assertions.assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book("1234567890", "Title", "Author", 9.90));
    }
}
