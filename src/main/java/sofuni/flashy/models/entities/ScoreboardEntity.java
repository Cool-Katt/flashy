package sofuni.flashy.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "scoreboards")
public class ScoreboardEntity extends BaseEntity
{
    private String playerName;
    private Long place;
}
