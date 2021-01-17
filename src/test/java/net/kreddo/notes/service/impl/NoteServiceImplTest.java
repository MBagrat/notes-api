package net.kreddo.notes.service.impl;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import net.kreddo.notes.dto.NoteDto;
import net.kreddo.notes.dto.UserDto;
import net.kreddo.notes.mapper.CycleAvoidingMappingContext;
import net.kreddo.notes.mapper.NoteMapper;
import net.kreddo.notes.model.NoteEntity;
import net.kreddo.notes.model.UserEntity;
import net.kreddo.notes.repository.NoteRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class NoteServiceImplTest {

  @Spy
  private NoteRepository noteRepository;

  @Spy
  private NoteMapper mapper;

  @Spy
  private CycleAvoidingMappingContext cycleAvoidingMappingContext;

  @InjectMocks
  private NoteServiceImpl noteService;

  private NoteEntity noteEntity;
  private NoteEntity noteEntityOne;
  private NoteEntity noteEntityTwo;
  private NoteDto noteDto;
  private NoteDto noteDtoOne;
  private NoteDto noteDtoTwo;

  @BeforeEach
  void setUp() {

    UserEntity userEntity = new UserEntity();
    userEntity.setEmail("test@gmail.com");
    userEntity.setPassword("Pa$$word!1");
    userEntity.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userEntity.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteEntity = new NoteEntity();
    noteEntity.setTitle("NoteEntity one title");
    noteEntity.setNote("NoteEntity One body");
    noteEntity.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteEntity.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteEntity.setUser(userEntity);

    UserEntity userEntityOne = new UserEntity();
    userEntityOne.setId(1L);
    userEntityOne.setEmail("test@gmail.com");
    userEntityOne.setPassword("Pa$$word!1");
    userEntityOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userEntityOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteEntityOne = new NoteEntity();
    noteEntityOne.setId(1L);
    noteEntityOne.setTitle("NoteEntity one title");
    noteEntityOne.setNote("NoteEntity one body");
    noteEntityOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteEntityOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteEntityOne.setUser(userEntityOne);

    UserEntity userEntityTwo = new UserEntity();
    userEntityTwo.setId(2L);
    userEntityTwo.setEmail("test@gmail.com");
    userEntityTwo.setPassword("Pa$$word!1");
    userEntityTwo.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userEntityTwo.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteEntityTwo = new NoteEntity();
    noteEntityTwo.setId(2L);
    noteEntityTwo.setTitle("NoteEntity two title");
    noteEntityTwo.setNote("NoteEntity two body");
    noteEntityTwo.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteEntityTwo.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteEntityTwo.setUser(userEntityTwo);

    UserDto userDto = new UserDto();
    userDto.setEmail("test@gmail.com");
    userDto.setPassword("Pa$$word!1");
    userDto.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userDto.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteDto = new NoteDto();
    noteDto.setTitle("NoteEntity one title");
    noteDto.setNote("NoteEntity one body");
    noteDto.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDto.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDto.setUser(userDto);

    UserDto userDtoOne = new UserDto();
    userDtoOne.setId(1L);
    userDtoOne.setEmail("test@gmail.com");
    userDtoOne.setPassword("Pa$$word!1");
    userDtoOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userDtoOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteDtoOne = new NoteDto();
    noteDtoOne.setId(1L);
    noteDtoOne.setTitle("NoteEntity one title");
    noteDtoOne.setNote("NoteEntity one body");
    noteDtoOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDtoOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDtoOne.setUser(userDtoOne);

    UserDto userDtoTwo = new UserDto();
    userDtoTwo.setId(2L);
    userDtoTwo.setEmail("test@gmail.com");
    userDtoTwo.setPassword("Pa$$word!1");
    userDtoTwo.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userDtoTwo.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteDtoTwo = new NoteDto();
    noteDtoTwo.setId(2L);
    noteDtoTwo.setTitle("NoteEntity two title");
    noteDtoTwo.setNote("NoteEntity two body");
    noteDtoTwo.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDtoTwo.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDtoTwo.setUser(userDtoTwo);
  }

  @Test
  void getAllNotes() {
    when(noteRepository.findAll()).thenReturn(Lists.newArrayList(noteEntityOne, noteEntityTwo));
    when(mapper.toNoteDtoList(Lists.newArrayList(noteEntityOne, noteEntityTwo), cycleAvoidingMappingContext)).thenReturn(Lists.newArrayList(
        noteDtoOne, noteDtoTwo));

    List<NoteDto> actualAllNotes = noteService.getAllNotes();

    assertThat(actualAllNotes, hasSize(2));
    assertThat(actualAllNotes.get(0), equalTo(noteDtoOne));
    assertThat(actualAllNotes.get(1), equalTo(noteDtoTwo));

    verify(noteRepository, atLeastOnce()).findAll();
    verify(mapper, atLeastOnce()).toNoteDtoList(Lists.newArrayList(noteEntityOne, noteEntityTwo),
        cycleAvoidingMappingContext);
  }

  @Test
  void getNote() {

    when(noteRepository.findById(1L)).thenReturn(Optional.of(noteEntityOne));
    when(mapper.toNoteDto(noteEntityOne, cycleAvoidingMappingContext)).thenReturn(noteDtoOne);

    NoteDto actualNote = noteService.getNote(1L);

    assertThat(actualNote, is(not(nullValue())));
    assertThat(actualNote, equalTo(noteDtoOne));

    verify(noteRepository, atLeastOnce()).findById(1L);
    verify(mapper, atLeastOnce()).toNoteDto(noteEntityOne, cycleAvoidingMappingContext);
  }

  @Test
  void addNote() {

    when(mapper.toNote(noteDto, cycleAvoidingMappingContext)).thenReturn(noteEntity);
    when(noteRepository.save(noteEntity)).thenReturn(noteEntityTwo);
    when(mapper.toNoteDto(noteEntityTwo, cycleAvoidingMappingContext)).thenReturn(noteDtoTwo);

    NoteDto actualNote = noteService.addNote(noteDto);

    assertThat(actualNote, is(not(nullValue())));
    assertThat(actualNote, equalTo(noteDtoTwo));

    verify(mapper, atLeastOnce()).toNote(noteDto, cycleAvoidingMappingContext);
    verify(noteRepository, atLeastOnce()).save(noteEntity);
    verify(mapper, atLeastOnce()).toNoteDto(noteEntityTwo, cycleAvoidingMappingContext);
  }

  @Test
  void updateNote() {

    noteEntityOne.setTitle("Update NoteEntity one title");
    noteDtoOne.setTitle("Update NoteEntity one title");

    when(mapper.toNote(noteDtoOne, cycleAvoidingMappingContext)).thenReturn(noteEntityOne);
    when(noteRepository.findById(noteEntityOne.getId())).thenReturn(Optional.of(noteEntityOne));
    when(noteRepository.save(noteEntityOne)).thenReturn(noteEntityOne);
    when(mapper.toNoteDto(noteEntityOne, cycleAvoidingMappingContext)).thenReturn(noteDtoOne);

    NoteDto actualNote = noteService.updateNote(1L, noteDtoOne);

    assertThat(actualNote, is(not(nullValue())));
    assertThat(actualNote, equalTo(noteDtoOne));
    assertThat(actualNote.getTitle(), is("Update NoteEntity one title"));

    verify(mapper, atLeastOnce()).toNote(noteDtoOne, cycleAvoidingMappingContext);
    verify(noteRepository, atLeastOnce()).findById(1L);
    verify(noteRepository, atLeastOnce()).save(noteEntityOne);
    verify(mapper, atLeastOnce()).toNoteDto(noteEntityOne, cycleAvoidingMappingContext);
  }

  @Test
  void deleteNote() {
    doNothing().when(noteRepository).deleteById(1L);

    noteService.deleteNote(1L);

    verify(noteRepository, atLeastOnce()).deleteById(1L);
  }

  @Test
  void testDeleteNote() {
    doNothing().when(noteRepository).delete(noteEntityOne);
    when(mapper.toNote(noteDtoOne, cycleAvoidingMappingContext)).thenReturn(noteEntityOne);

    noteService.deleteNote(noteDtoOne);

    verify(noteRepository, atLeastOnce()).delete(noteEntityOne);
  }
}