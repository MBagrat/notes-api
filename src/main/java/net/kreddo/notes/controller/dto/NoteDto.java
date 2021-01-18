package net.kreddo.notes.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteDto implements Serializable {

  @JsonProperty
  private Long id;

  @NotEmpty(message = "Note title can't be empty")
  @Size(max = 50, message = "Note title can't be more than 50 characters")
  @JsonProperty
  private String title;

  @Size(max = 1000, message = "Note can't be more than 1000 characters")
  @JsonProperty
  private String note;

  @JsonProperty
  private LocalDateTime createTime;

  @JsonProperty
  private LocalDateTime lastUpdateTime;

  @JsonProperty
  private UserDto user;

}
