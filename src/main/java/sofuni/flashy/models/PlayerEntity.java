package sofuni.flashy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "players")
public class PlayerEntity extends BaseEntity
{
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password")
    private String passwordHash;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")

    private List<RoleEntity> roles = new ArrayList<>();
}
