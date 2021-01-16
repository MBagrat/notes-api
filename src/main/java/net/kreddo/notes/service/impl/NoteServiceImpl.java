package net.kreddo.notes.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kreddo.notes.dto.NoteDto;
import net.kreddo.notes.mapper.NoteMapper;
import net.kreddo.notes.model.Note;
import net.kreddo.notes.repository.NoteRepository;
import net.kreddo.notes.service.NoteService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

  private final NoteMapper noteMapper;

  private final NoteRepository noteRepository;

  @Override
  public NoteDto getNote(Long id) {
    return noteMapper.toNoteDto(noteRepository.findById(id).orElseThrow());
  }

  @Override
  public List<NoteDto> getAllNotes() {
    return noteMapper.toNoteDtoList(noteRepository.findAll());
  }

  @Override
  public NoteDto addNote(NoteDto noteDto) {
    return noteMapper.toNoteDto(noteRepository.save(noteMapper.toNote(noteDto)));
  }

  @Override
  public NoteDto updateNote(Long id, NoteDto noteDto) {
    Note noteModel = noteMapper.toNote(noteDto);
    Note note = null;
    if (noteRepository.findById(id).isPresent()) {
      note = noteRepository.save(noteModel);
    }
    return noteMapper.toNoteDto(note);
  }

  @Override
  public void deleteNote(Long id) {
    noteRepository.deleteById(id);
  }

  @Override
  public void deleteNote(NoteDto noteDto) {
    noteRepository.delete(noteMapper.toNote(noteDto));
  }
}
