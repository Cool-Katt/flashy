package sofuni.flashy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofuni.flashy.models.entities.ScoreboardEntity;

@Repository
public interface ScoreboardRepository extends JpaRepository<ScoreboardEntity, Long>
{
    void deleteAll();
}
