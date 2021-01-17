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
import net.kreddo.notes.model.Note;
import net.kreddo.notes.model.User;
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

  private Note note;
  private Note noteOne;
  private Note noteTwo;
  private NoteDto noteDto;
  private NoteDto noteOneDto;
  private NoteDto noteTwoDto;

  @BeforeEach
  void setUp() {

    User user = new User();
    user.setEmail("test@gmail.com");
    user.setPassword("Pa$$word!1");
    user.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    user.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    note = new Note();
    note.setTitle("Note one title");
    note.setNote("Note One body");
    note.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    note.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    note.setUser(user);

    User userOne = new User();
    userOne.setId(1L);
    userOne.setEmail("test@gmail.com");
    userOne.setPassword("Pa$$word!1");
    userOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteOne = new Note();
    noteOne.setId(1L);
    noteOne.setTitle("Note one title");
    noteOne.setNote("Note one body");
    noteOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteOne.setUser(userOne);

    User userTwo = new User();
    userTwo.setId(2L);
    userTwo.setEmail("test@gmail.com");
    userTwo.setPassword("Pa$$word!1");
    userTwo.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userTwo.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteTwo = new Note();
    noteTwo.setId(2L);
    noteTwo.setTitle("Note two title");
    noteTwo.setNote("Note two body");
    noteTwo.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteTwo.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteTwo.setUser(userTwo);

    UserDto userDto = new UserDto();
    userDto.setEmail("test@gmail.com");
    userDto.setPassword("Pa$$word!1");
    userDto.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userDto.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteDto = new NoteDto();
    noteDto.setTitle("Note one title");
    noteDto.setNote("Note one body");
    noteDto.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDto.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteDto.setUser(userDto);

    UserDto userOneDto = new UserDto();
    userOneDto.setId(1L);
    userOneDto.setEmail("test@gmail.com");
    userOneDto.setPassword("Pa$$word!1");
    userOneDto.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userOneDto.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteOneDto = new NoteDto();
    noteOneDto.setId(1L);
    noteOneDto.setTitle("Note one title");
    noteOneDto.setNote("Note one body");
    noteOneDto.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteOneDto.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteOneDto.setUser(userOneDto);

    UserDto userTwoDto = new UserDto();
    userTwoDto.setId(2L);
    userTwoDto.setEmail("test@gmail.com");
    userTwoDto.setPassword("Pa$$word!1");
    userTwoDto.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userTwoDto.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteTwoDto = new NoteDto();
    noteTwoDto.setId(2L);
    noteTwoDto.setTitle("Note two title");
    noteTwoDto.setNote("Note two body");
    noteTwoDto.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteTwoDto.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteTwoDto.setUser(userTwoDto);
  }

  @Test
  void getAllNotes() {
    when(noteRepository.findAll()).thenReturn(Lists.newArrayList(noteOne, noteTwo));
    when(mapper.toNoteDtoList(Lists.newArrayList(noteOne, noteTwo), cycleAvoidingMappingContext)).thenReturn(Lists.newArrayList(noteOneDto, noteTwoDto));

    List<NoteDto> actualAllNotes = noteService.getAllNotes();

    assertThat(actualAllNotes, hasSize(2));
    assertThat(actualAllNotes.get(0), equalTo(noteOneDto));
    assertThat(actualAllNotes.get(1), equalTo(noteTwoDto));

    verify(noteRepository, atLeastOnce()).findAll();
    verify(mapper, atLeastOnce()).toNoteDtoList(Lists.newArrayList(noteOne, noteTwo),
        cycleAvoidingMappingContext);
  }

  @Test
  void getNote() {

    when(noteRepository.findById(1L)).thenReturn(Optional.of(noteOne));
    when(mapper.toNoteDto(noteOne, cycleAvoidingMappingContext)).thenReturn(noteOneDto);

    NoteDto actualNote = noteService.getNote(1L);

    assertThat(actualNote, is(not(nullValue())));
    assertThat(actualNote, equalTo(noteOneDto));

    verify(noteRepository, atLeastOnce()).findById(1L);
    verify(mapper, atLeastOnce()).toNoteDto(noteOne, cycleAvoidingMappingContext);
  }

  @Test
  void addNote() {

    when(mapper.toNote(noteDto, cycleAvoidingMappingContext)).thenReturn(note);
    when(noteRepository.save(note)).thenReturn(noteTwo);
    when(mapper.toNoteDto(noteTwo, cycleAvoidingMappingContext)).thenReturn(noteTwoDto);

    NoteDto actualNote = noteService.addNote(noteDto);

    assertThat(actualNote, is(not(nullValue())));
    assertThat(actualNote, equalTo(noteTwoDto));

    verify(mapper, atLeastOnce()).toNote(noteDto, cycleAvoidingMappingContext);
    verify(noteRepository, atLeastOnce()).save(note);
    verify(mapper, atLeastOnce()).toNoteDto(noteTwo, cycleAvoidingMappingContext);
  }

  @Test
  void updateNote() {

    noteOne.setTitle("Update Note one title");
    noteOneDto.setTitle("Update Note one title");

    when(mapper.toNote(noteOneDto, cycleAvoidingMappingContext)).thenReturn(noteOne);
    when(noteRepository.findById(noteOne.getId())).thenReturn(Optional.of(noteOne));
    when(noteRepository.save(noteOne)).thenReturn(noteOne);
    when(mapper.toNoteDto(noteOne, cycleAvoidingMappingContext)).thenReturn(noteOneDto);

    NoteDto actualNote = noteService.updateNote(1L, noteOneDto);

    assertThat(actualNote, is(not(nullValue())));
    assertThat(actualNote, equalTo(noteOneDto));
    assertThat(actualNote.getTitle(), is("Update Note one title"));

    verify(mapper, atLeastOnce()).toNote(noteOneDto, cycleAvoidingMappingContext);
    verify(noteRepository, atLeastOnce()).findById(1L);
    verify(noteRepository, atLeastOnce()).save(noteOne);
    verify(mapper, atLeastOnce()).toNoteDto(noteOne, cycleAvoidingMappingContext);
  }

  @Test
  void deleteNote() {
    doNothing().when(noteRepository).deleteById(1L);

    noteService.deleteNote(1L);

    verify(noteRepository, atLeastOnce()).deleteById(1L);
  }

  @Test
  void testDeleteNote() {
    doNothing().when(noteRepository).delete(noteOne);
    when(mapper.toNote(noteOneDto, cycleAvoidingMappingContext)).thenReturn(noteOne);

    noteService.deleteNote(noteOneDto);

    verify(noteRepository, atLeastOnce()).delete(noteOne);
  }
}