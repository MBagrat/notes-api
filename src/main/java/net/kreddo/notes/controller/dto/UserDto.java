package net.kreddo.notes.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import net.kreddo.notes.controller.validation.ValidPassword;
import net.kreddo.notes.repository.model.NoteEntity;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

  @JsonProperty
  private Long id;

  @Email(message = "Please provide a valid email address")
  @NotEmpty(message = "E-mail address can't be empty")
  @JsonProperty(value = "username")
  private String email;

  @JsonProperty
  @ValidPassword
  @NotEmpty(message = "Password can't be empty.")
  @JsonInclude(Include.NON_NULL)
  private String password;

  @JsonProperty
  private LocalDateTime createTime;

  @JsonProperty
  private LocalDateTime lastUpdateTime;

  @JsonIgnore
  private List<NoteEntity> notes;

}
