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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "notes")
@NoArgsConstructor
public class Note {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", length = 50)
  private String title;

  @Column(name="note", length = 1000)
  private String note;

  @Column(name="create_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime createTime;

  @Column(name="last_update_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime lastUpdateTime;

  @ManyToOne
  private User user;

}
