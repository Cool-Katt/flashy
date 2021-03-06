package sofuni.flashy.models.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
}
