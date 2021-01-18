package net.kreddo.notes.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kreddo.notes.controller.dto.NoteDto;
import net.kreddo.notes.repository.NoteRepository;
import net.kreddo.notes.repository.model.NoteEntity;
import net.kreddo.notes.service.NoteService;
import net.kreddo.notes.service.mapper.CycleAvoidingMappingContext;
import net.kreddo.notes.service.mapper.NoteMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

  private final NoteMapper noteMapper;

  private final CycleAvoidingMappingContext cycleAvoidingMappingContext;

  private final NoteRepository noteRepository;

  @Override
  public NoteDto getNote(Long id) {
    return noteMapper.toNoteDto(noteRepository.findById(id).orElseThrow(),
        cycleAvoidingMappingContext);
  }

  @Override
  public List<NoteDto> getAllNotes() {
    return noteMapper.toNoteDtoList(noteRepository.findAll(), cycleAvoidingMappingContext);
  }

  @Override
  public NoteDto addNote(NoteDto noteDto) {
    return noteMapper.toNoteDto(noteRepository.save(noteMapper.toNote(noteDto,
        cycleAvoidingMappingContext)), cycleAvoidingMappingContext);
  }

  @Override
  public NoteDto updateNote(NoteDto noteDto) {
    var existingNote = noteMapper.toNote(noteDto, cycleAvoidingMappingContext);
    NoteEntity note = null;
    if (noteRepository.findById(existingNote.getId()).isPresent()) {
      note = noteRepository.save(existingNote);
    }
    return noteMapper.toNoteDto(note, cycleAvoidingMappingContext);
  }

  @Override
  public void deleteNote(Long id) {
    noteRepository.deleteById(id);
  }

  @Override
  public void deleteNote(NoteDto noteDto) {
    noteRepository.delete(noteMapper.toNote(noteDto, cycleAvoidingMappingContext));
  }
}
