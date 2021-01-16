package net.kreddo.notes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kreddo.notes.model.Note;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

  @JsonProperty
  private Long id;

  @JsonProperty
  private String email;

  @JsonProperty
  private String password;

  @JsonProperty
  private LocalDateTime createTime;

  @JsonProperty
  private LocalDateTime lastUpdateTime;

  @JsonIgnore
  private List<Note> notes;

}
