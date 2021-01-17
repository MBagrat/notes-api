package net.kreddo.notes.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteDto implements Serializable {

  @JsonProperty
  private Long id;

  @JsonProperty
  private String title;

  @JsonProperty
  private String note;

  @JsonProperty
  private LocalDateTime createTime;

  @JsonProperty
  private LocalDateTime lastUpdateTime;

  @JsonProperty
  private UserDto user;

}
