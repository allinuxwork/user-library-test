package userlibrarytest.service;

import userlibrarytest.model.Book;
import userlibrarytest.model.Reader;
import userlibrarytest.repository.ReaderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReaderService {

    private ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public List<Book> getBooksByReaderId(Long id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) {
            return reader.get().getBooks();
        } else {
            return new ArrayList<>();
        }
    }
}