package userlibrarytest.controller;

import userlibrarytest.model.Book;
import userlibrarytest.model.Reader;
import userlibrarytest.service.AuthorService;
import userlibrarytest.service.BookService;
import userlibrarytest.service.ReaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryController {

    private AuthorService authorService;

    private BookService bookService;

    private ReaderService readerService;

    public LibraryController(AuthorService authorService, BookService bookService, ReaderService readerService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping("/findBooksByReader/{id}")
    @ResponseBody
    public List<Book> findBooksByReader(@PathVariable Long id) {
        return readerService.getBooksByReaderId(id);
    }

    @GetMapping("/findBooksByAuthor/{id}")
    @ResponseBody
    public List<Book> findBooksByAuthor(@PathVariable Long id) {
        return authorService.getBooksByAuthorId(id);
    }

    @GetMapping("/getReaderByBook/{id}")
    @ResponseBody
    public Reader getReaderByBookId(@PathVariable Long id) {
        return bookService.getReaderByBookId(id);
    }

    @GetMapping("/getReadersByAuthor/{id}")
    @ResponseBody
    public List<Reader> getReadersByAuthorId(@PathVariable Long id) {
        return authorService.getReadersByAuthorId(id);
    }

}