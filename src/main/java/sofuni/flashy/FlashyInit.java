package sofuni.flashy;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import sofuni.flashy.services.PlayerService;

@Component
public class FlashyInit implements ApplicationRunner
{
    private final PlayerService playerService;

    public FlashyInit(PlayerService playerService)
    {
        this.playerService = playerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        this.playerService.init();
    }
}
