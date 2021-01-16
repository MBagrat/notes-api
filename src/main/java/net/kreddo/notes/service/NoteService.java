package net.kreddo.notes.service;

import java.util.List;
import net.kreddo.notes.dto.NoteDto;
import org.springframework.stereotype.Service;

public interface NoteService {

  NoteDto getNote(Long id);

  List<NoteDto> getAllNotes();

  NoteDto addNote(NoteDto noteDto);

  NoteDto updateNote(Long id, NoteDto noteDto);

  void deleteNote(Long id);

  void deleteNote(NoteDto noteDto);
}
