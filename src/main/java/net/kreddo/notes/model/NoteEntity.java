package net.kreddo.notes.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "notes")
@NoArgsConstructor
public class NoteEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 50)
  @Column(name = "title", nullable = false)
  private String title;

  @Size(max = 1000)
  @Column(name="note")
  private String note;

  @Column(name="create_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime createTime;

  @Column(name="last_update_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime lastUpdateTime;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

}
