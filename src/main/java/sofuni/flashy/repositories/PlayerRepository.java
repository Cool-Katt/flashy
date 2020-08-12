package sofuni.flashy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofuni.flashy.models.entities.PlayerEntity;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>
{
    Optional<PlayerEntity> findByEmail(String email);
}
