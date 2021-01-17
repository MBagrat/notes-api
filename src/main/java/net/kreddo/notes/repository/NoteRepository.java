package net.kreddo.notes.repository;

import net.kreddo.notes.model.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link NoteEntity} entity.
 */
@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {}
