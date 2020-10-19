package userlibrarytest;

import userlibrarytest.model.Author;
import userlibrarytest.model.Book;
import userlibrarytest.model.Reader;
import userlibrarytest.service.AuthorService;
import userlibrarytest.service.BookService;
import userlibrarytest.service.ReaderService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class LibraryApplicationTests {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    private List<Author> authorList;

    private List<Reader> readerList;

    private List<Book> bookList;

    @BeforeAll
    public void libraryData() {
        authorList = new ArrayList<>();
        authorList.add(new Author("Robert Martin"));
        authorList.add(new Author("Joshua Bloch"));
        authorList = authorList
                .stream()
                .peek(authorService::save)
                .collect(Collectors.toList());

        readerList = new ArrayList<>();
        readerList.add(new Reader("Galkina"));
        readerList.add(new Reader("Semenov"));
        readerList = readerList
                .stream()
                .peek(readerService::save)
                .collect(Collectors.toList());

        bookList = new ArrayList<>();
        bookList.add(new Book("Clean code", authorList.get(0), readerList.get(0)));
        bookList.add(new Book("The perfect programmer", authorList.get(0), readerList.get(0)));
        bookList.add(new Book("Clean architecture", authorList.get(0), null));
        bookList.add(new Book("Effective Java", authorList.get(1), readerList.get(1)));
        bookList.add(new Book("Cloud Native Java", authorList.get(1), readerList.get(1)));
        bookList = bookList
                .stream()
                .peek(bookService::save)
                .collect(Collectors.toList());
    }

    @Test
    public void findBooksByUserTest(){
        Long id = readerList.get(0).getId();
        List<Book> books = readerService.getBooksByReaderId(id);
        Assertions.assertThat(books).hasSize(2);
        Assertions.assertThat(books.stream().map(Book::getName)
                .collect(Collectors.toList())).contains("The perfect programmer","Clean code");

    }

    @Test
    public void findBooksByAuthorTest(){
        Long id = authorList.get(0).getId();
        List<Book> books = authorService.getBooksByAuthorId(id);
        Assertions.assertThat(books).hasSize(3);
        id = authorList.get(1).getId();
        books = authorService.getBooksByAuthorId(id);
        Assertions.assertThat(books).hasSize(2);
    }

    @Test
    public void findReaderByBookTest(){
        Long id = bookList.get(1).getId();
        Reader reader = bookService.getReaderByBookId(id);
        Assertions.assertThat(reader.getName()).isEqualTo("Galkina");
    }

    @Test
    public void findReadersByAuthorTest(){
        Long id = authorList.get(1).getId();
        List<Reader> readerList = authorService.getReadersByAuthorId(id);
        Assertions.assertThat(readerList).hasSize(1);
    }

}