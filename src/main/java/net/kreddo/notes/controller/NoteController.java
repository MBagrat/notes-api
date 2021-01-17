package net.kreddo.notes.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.kreddo.notes.controller.dto.NoteDto;
import net.kreddo.notes.service.NoteService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public NoteDto getNote(@PathVariable("id") Long id) {
    return noteService.getNote(id);
  }

  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  public NoteDto addNote(@RequestBody NoteDto noteDto) {
    return noteService.addNote(noteDto);
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public NoteDto updateNote(@RequestBody NoteDto noteDto) {
    return noteService.updateNote(noteDto);
  }

  @DeleteMapping("/{id}")
  public void deleteNote(@PathVariable("id") Long id) {
    noteService.deleteNote(id);
  }

  @DeleteMapping("/note")
  public void deleteNote(@RequestBody NoteDto noteDto) {
    noteService.deleteNote(noteDto);
  }

}

