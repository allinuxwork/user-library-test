package userlibrarytest.repository;

import userlibrarytest.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
