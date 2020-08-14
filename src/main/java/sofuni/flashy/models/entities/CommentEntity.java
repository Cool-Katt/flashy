package sofuni.flashy.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Comments")
public class CommentEntity extends BaseEntity
{
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "username", nullable = false)
    private String leftBy;
}
