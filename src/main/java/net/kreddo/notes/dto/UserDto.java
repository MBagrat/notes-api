package net.kreddo.notes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import net.kreddo.notes.model.NoteEntity;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

  @JsonProperty
  private Long id;

  @JsonProperty(value = "username")
  private String email;

  @JsonProperty
  @JsonInclude(Include.NON_NULL)
  private String password;

  @JsonProperty
  private LocalDateTime createTime;

  @JsonProperty
  private LocalDateTime lastUpdateTime;

  @JsonIgnore
  private List<NoteEntity> notes;

}
