package net.kreddo.notes.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kreddo.notes.controller.dto.NoteDto;
import net.kreddo.notes.controller.exception.InvalidInputException;
import net.kreddo.notes.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

  private final NoteService noteService;

  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<NoteDto> getAllNotes() {
    return noteService.getAllNotes();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NoteDto> getNote(@PathVariable("id") Long id) {
    if (null == id || id.equals(0L)) {
      throw new InvalidInputException("Invalid Note id");
    }
    NoteDto note = noteService.getNote(id);
    return new ResponseEntity<>(note, HttpStatus.OK);
  }

  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NoteDto> addNote(@RequestBody @Valid NoteDto noteDto) {
    if (noteDto.getId() != null) {
      throw new InvalidInputException("Note Id should not exist");
    }
    NoteDto note = noteService.addNote(noteDto);
    return new ResponseEntity<>(note, HttpStatus.CREATED);
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NoteDto> updateNote(@RequestBody @Valid NoteDto noteDto) {
    if (noteDto.getId() == null) {
      throw new InvalidInputException("Note Id should exist");
    }
    NoteDto note = noteService.updateNote(noteDto);
    return new ResponseEntity<>(note, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteNote(@PathVariable("id") Long id) {
    if (null == id || id.equals(0L)) {
      throw new InvalidInputException("Invalid Note id");
    }
    noteService.deleteNote(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/note")
  public ResponseEntity<?> deleteNote(@RequestBody @Valid NoteDto noteDto) {
    if (noteDto.getId() == null) {
      throw new InvalidInputException("Note Id should exist");
    }
    noteService.deleteNote(noteDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}

