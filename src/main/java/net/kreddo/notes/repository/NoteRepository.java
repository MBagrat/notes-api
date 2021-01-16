package net.kreddo.notes.repository;

import net.kreddo.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Note} entity.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {}
