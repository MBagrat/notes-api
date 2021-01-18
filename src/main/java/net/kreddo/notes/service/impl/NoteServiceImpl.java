package net.kreddo.notes.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kreddo.notes.controller.dto.NoteDto;
import net.kreddo.notes.repository.NoteRepository;
import net.kreddo.notes.repository.model.NoteEntity;
import net.kreddo.notes.service.NoteService;
import net.kreddo.notes.service.exception.BusinessException;
import net.kreddo.notes.service.exception.EntityAlreadyExistException;
import net.kreddo.notes.service.exception.EntityNotFoundException;
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
    NoteEntity retrievedNote = noteRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Note", "id", id.toString()));
    return noteMapper.toNoteDto(retrievedNote,cycleAvoidingMappingContext);
  }

  @Override
  public List<NoteDto> getAllNotes() {
    List<NoteEntity> retrievedNotes = noteRepository.findAll();
    return noteMapper.toNoteDtoList(retrievedNotes, cycleAvoidingMappingContext);
  }

  @Override
  public NoteDto addNote(NoteDto noteDto) {
    var note = noteMapper.toNote(noteDto, cycleAvoidingMappingContext);
    if(noteRepository.findById(note.getId()).isPresent()) {
      throw new EntityAlreadyExistException("Note", "id", note.getId().toString());
    };
    if (note.getTitle() != null) {
      return noteMapper.toNoteDto(noteRepository.save(note), cycleAvoidingMappingContext);
    } else {
      throw new BusinessException("Note title can't be empty");
    }
  }

  @Override
  public NoteDto updateNote(NoteDto noteDto) {
    var note = noteMapper.toNote(noteDto, cycleAvoidingMappingContext);
    if (noteRepository.findById(note.getId()).isPresent()) {
      return noteMapper.toNoteDto(noteRepository.save(note), cycleAvoidingMappingContext);
    } else {
      throw new EntityNotFoundException("Note", "id", note.getId().toString());
    }
  }

  @Override
  public void deleteNote(Long id) {
    if (noteRepository.findById(id).isPresent()) {
      noteRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException("Note", "id", id.toString());
    }
  }

  @Override
  public void deleteNote(NoteDto noteDto) {
    var note = noteMapper.toNote(noteDto, cycleAvoidingMappingContext);
    if (noteRepository.findById(note.getId()).isPresent()) {
      noteRepository.delete(note);
    } else {
      throw new EntityNotFoundException("Note", "id", note.getId().toString());
    }
  }
}
