package sofuni.flashy.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.flashy.models.serviceModels.PlayerServiceModel;
import sofuni.flashy.repositories.PlayerRepository;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class StatsService
{
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private AtomicInteger requestCount = new AtomicInteger(0);
    private Instant startedOn = Instant.now();

    public StatsService(PlayerRepository playerRepository, ModelMapper modelMapper)
    {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }

    public void incRequestCount()
    {
        this.requestCount.incrementAndGet();
    }

    public int getRequestCount()
    {
        return this.requestCount.get();
    }

    public Instant getStartedOn()
    {
        return this.startedOn;
    }

    public List<PlayerServiceModel> showAllPlayers()
    {
        return this.playerRepository.findAll().stream()
                .map(l -> this.modelMapper.map(l, PlayerServiceModel.class)).collect(Collectors.toList());
    }
}
