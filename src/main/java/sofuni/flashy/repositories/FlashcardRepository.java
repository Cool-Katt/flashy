package sofuni.flashy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofuni.flashy.models.entities.FlashcardEntity;

import java.util.Optional;

@Repository
public interface FlashcardRepository extends JpaRepository<FlashcardEntity, Long>
{
    Optional<FlashcardEntity> findByTitle(String title);

    void deleteByTitle(String title);
}
