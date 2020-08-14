package sofuni.flashy.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sofuni.flashy.models.entities.PlayerEntity;
import sofuni.flashy.models.entities.ScoreboardEntity;
import sofuni.flashy.models.serviceModels.PlayerServiceModel;
import sofuni.flashy.models.serviceModels.ScoreboardServiceModel;
import sofuni.flashy.repositories.PlayerRepository;
import sofuni.flashy.repositories.ScoreboardRepository;
import sofuni.flashy.services.ScoreboardService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreboardServiceImpl implements ScoreboardService
{
    private final PlayerRepository playerRepository;
    private final ScoreboardRepository scoreboardRepository;
    private final ModelMapper modelMapper;

    public ScoreboardServiceImpl(PlayerRepository playerRepository, ScoreboardRepository scoreboardRepository, ModelMapper modelMapper)
    {
        this.playerRepository = playerRepository;
        this.scoreboardRepository = scoreboardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ScoreboardServiceModel> showScoreboard()
    {
        return this.scoreboardRepository.findAll().stream()
                .map(s -> this.modelMapper.map(s, ScoreboardServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void populateScoreboard()
    {
        this.playerRepository.findAll().stream().sorted((o1, o2) -> o2.getDeck().size() - o1.getDeck().size()).forEach(p ->
        {
            ScoreboardEntity scoreboardEntity = new ScoreboardEntity();
            scoreboardEntity.setPlayerName(p.getEmail());
            scoreboardEntity.setPlace((long) p.getDeck().size());
            this.scoreboardRepository.saveAndFlush(scoreboardEntity);
        });
    }

    @Override
    public void resetScoreboard()
    {
        this.scoreboardRepository.deleteAll();
    }

    @Scheduled(cron = "*/15 * * * * *")
    void reset()
    {
        this.resetScoreboard();
        this.populateScoreboard();
    }
}
