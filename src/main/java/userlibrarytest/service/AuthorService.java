package userlibrarytest.service;

import userlibrarytest.model.Author;
import userlibrarytest.model.Book;
import userlibrarytest.model.Reader;
import userlibrarytest.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private AuthorRepository authorRepository;

    private BookService bookService;

    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    public List<Book> getBooksByAuthorId(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get().getBooks();
        } else {
            return new ArrayList<>();
        }
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public List<Reader> getReadersByAuthorId(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            List<Book> books = author.get().getBooks();
            if (books != null) {
                return books.stream()
                        .map(it -> bookService.getReaderByBookId(it.getId()))
                        .filter(it -> it != null)
                        .distinct()
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

}