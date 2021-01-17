package net.kreddo.notes.controller;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import net.kreddo.notes.config.NotesApiProperties;
import net.kreddo.notes.controller.dto.NoteDto;
import net.kreddo.notes.controller.dto.UserDto;
import net.kreddo.notes.service.NoteService;
import net.kreddo.notes.service.impl.UserDetailsServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private UserDetailsServiceImpl userDetailsService;

  @MockBean
  private BCryptPasswordEncoder passwordEncoder;

  @MockBean
  private NoteService noteService;

  private NoteDto note;
  private NoteDto noteOne;
  private NoteDto noteTwo;

  @BeforeEach
  void setUp() {
    var user = new UserDto();
    user.setEmail("test@gmail.com");
    user.setPassword("Pa$$word!1");
    user.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    user.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    var userOne = new UserDto();
    userOne.setId(1L);
    userOne.setEmail("test@gmail.com");
    userOne.setPassword("Pa$$word!1");
    userOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    var userTwo = new UserDto();
    userTwo.setId(2L);
    userTwo.setEmail("test@gmail.com");
    userTwo.setPassword("Pa$$word!1");
    userTwo.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    userTwo.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));

    noteOne = new NoteDto();
    noteOne.setId(1L);
    noteOne.setTitle("Note one title");
    noteOne.setNote("Note one body");
    noteOne.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteOne.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteOne.setUser(userOne);

    noteTwo = new NoteDto();
    noteTwo.setId(2L);
    noteTwo.setTitle("Note two title");
    noteTwo.setNote("Note two body");
    noteTwo.setCreateTime(LocalDateTime.parse("2017-06-13 08:56:22",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteTwo.setLastUpdateTime(LocalDateTime.parse("2020-10-08 13:05:43",ofPattern("yyyy-MM-dd HH:mm:ss")));
    noteTwo.setUser(userTwo);

    note = new NoteDto();
    note.setTitle("Note one title");
    note.setNote("Note one body");
    note.setCreateTime(LocalDateTime.parse("2015-10-23 03:34:40",ofPattern("yyyy-MM-dd HH:mm:ss")));
    note.setLastUpdateTime(LocalDateTime.parse("2020-03-18 05:18:32",ofPattern("yyyy-MM-dd HH:mm:ss")));
    note.setUser(user);
  }

  @Test
  void getAllNotes() throws Exception {

    when(noteService.getAllNotes()).thenReturn(Lists.newArrayList(noteOne, noteTwo));

    this.mockMvc
        .perform(get("/notes/").with(csrf()).with(jwt()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*]", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].title", is("Note one title")))
        .andExpect(jsonPath("$[0].note", is("Note one body")))
        .andExpect(jsonPath("$[0].createTime", is(LocalDateTime.of(2015, 10, 23,3,34,40).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))))
        .andExpect(jsonPath("$[0].lastUpdateTime", is(LocalDateTime.of(2020, 3, 18,5,18,32).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].title", is("Note two title")))
        .andExpect(jsonPath("$[1].note", is("Note two body")))
        .andExpect(jsonPath("$[1].createTime", is(LocalDateTime.of(2017, 6, 13,8,56,22).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))))
        .andExpect(jsonPath("$[1].lastUpdateTime", is(LocalDateTime.of(2020, 10, 8,13,5,43).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))));

    verify(noteService, atLeastOnce()).getAllNotes();
  }

  @Test
  void getNote() throws Exception {

    when(noteService.getNote(1L)).thenReturn(noteOne);

    this.mockMvc
        .perform(get("/notes/1").with(csrf()).with(jwt()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(1)))
        .andExpect(jsonPath("title", is("Note one title")))
        .andExpect(jsonPath("note", is("Note one body")))
        .andExpect(jsonPath("createTime", is(LocalDateTime.of(2015, 10, 23,3,34,40).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))))
        .andExpect(jsonPath("lastUpdateTime", is(LocalDateTime.of(2020, 3, 18,5,18,32).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))));

    verify(noteService, atLeastOnce()).getNote(1L);
  }

  @Test
  void addNote() throws Exception {

    when(noteService.addNote(note)).thenReturn(noteOne);

    this.mockMvc
        .perform(post("/notes/").with(csrf()).with(jwt())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(note)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(1)))
        .andExpect(jsonPath("title", is("Note one title")))
        .andExpect(jsonPath("note", is("Note one body")))
        .andExpect(jsonPath("createTime", is(LocalDateTime.of(2015, 10, 23,3,34,40).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))))
        .andExpect(jsonPath("lastUpdateTime", is(LocalDateTime.of(2020, 3, 18,5,18,32).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))));

    verify(noteService, atLeastOnce()).addNote(note);
  }

  @Test
  void updateNote() throws Exception {

    noteOne.setTitle("Note one updated title");
    noteOne.setNote("Note one updated body");
    note.setLastUpdateTime(LocalDateTime.parse("2020-05-23 04:54:23",ofPattern("yyyy-MM-dd HH:mm:ss")));

    note.setId(1L);
    note.setTitle("Note one updated title");
    note.setNote("Note one updated body");
    note.setLastUpdateTime(LocalDateTime.parse("2020-05-23 04:54:23",ofPattern("yyyy-MM-dd HH:mm:ss")));

    when(noteService.updateNote(noteOne)).thenReturn(note);

    this.mockMvc
        .perform(patch("/notes/1").with(csrf()).with(jwt())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(noteOne)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(1)))
        .andExpect(jsonPath("title", is("Note one updated title")))
        .andExpect(jsonPath("note", is("Note one updated body")))
        .andExpect(jsonPath("createTime", is(LocalDateTime.of(2015, 10, 23,3,34,40).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))))
        .andExpect(jsonPath("lastUpdateTime", is(LocalDateTime.of(2020, 5, 23,4,54,23).format(ofPattern("yyyy-MM-dd'T'HH:mm:ss")))));

    verify(noteService, atLeastOnce()).updateNote(noteOne);
  }

  @Test
  void deleteNote() throws Exception {
    doNothing().when(noteService).deleteNote(1L);

    this.mockMvc
        .perform(delete("/notes/1").with(csrf()).with(jwt()))
        .andDo(print())
        .andExpect(status().isOk());

    verify(noteService, atLeastOnce()).deleteNote(1L);
  }

  @Test
  void testDeleteNote() throws Exception {
    doNothing().when(noteService).deleteNote(noteOne);

    this.mockMvc
        .perform(delete("/notes/note").with(csrf()).with(jwt())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(noteOne)))
        .andDo(print())
        .andExpect(status().isOk());

    verify(noteService, atLeastOnce()).deleteNote(noteOne);
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public NotesApiProperties notesApiProperties(){
      return new NotesApiProperties();
    }
  }
}
