package com.xpinjection.library.adaptors.api;

import com.xpinjection.library.service.BookService;
import com.xpinjection.library.service.dto.BookDto;
import com.xpinjection.library.service.dto.Books;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Alimenkou Mikalai
 */
@Tag(name = "books")
@Validated
@RestController
@AllArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BookDto> findBooksByAuthor(@RequestParam @NotBlank String author) {
        return bookService.findBooksByAuthor(author);
    }

    @PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BookDto> addBooks(@RequestBody Map<String, String> books)  {
        return bookService.addBooks(Books.fromMap(books));
    }

}
