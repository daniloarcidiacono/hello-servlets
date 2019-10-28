package com.objectway.stage.helloservlets.books.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.objectway.stage.helloservlets.books.dto.ErrorDTO;
import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.service.BookService;
import com.objectway.stage.helloservlets.test.utils.ObjectMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * In this case we load a Spring’s {@link WebApplicationContext} but there is no web server deployed.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BooksController.class)
@ActiveProfiles({ "memory", "test" }) // Do not confuse with @Profile
public class BooksControllerTest {
    private static final List<Book> stubBooks = Arrays.asList(
		new Book()
			.setTitle("title1")
			.setYear(1)
			.setAuthors(Arrays.asList("a1", "a2"))
			.setIsbn("1"),
		new Book()
			.setTitle("title2")
			.setYear(1)
			.setAuthors(Arrays.asList("a1", "a2"))
			.setIsbn("1")
			.setLink("link2")
	);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void throwError() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/throwError")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isInternalServerError())
        .andExpect(
            ObjectMatcher.isEquals(
                new ErrorDTO("This is designed to throw an exception!"),
                new TypeReference<ErrorDTO>() { }
            )
        );
    }

    @Test
    public void getAllBooks() throws Exception {
        Mockito.when(bookService.getAllBooks()).thenReturn(stubBooks);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(
            ObjectMatcher.isEquals(
                stubBooks,
                new TypeReference<List<Book>>() { }
            )
        );
    }

    @Test
    public void searchBookByISBN() {
    }

    @Test
    public void saveBook() {
    }

    @Test
    public void deleteBookByISBN() {
    }
}
