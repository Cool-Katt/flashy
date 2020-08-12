package sofuni.flashy.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "players")
public class PlayerEntity extends BaseEntity
{
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String passwordHash;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private List<RoleEntity> roles = new ArrayList<>();
}
