package sofuni.flashy.config;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sofuni.flashy.services.ScoreboardService;

@Component
public class MyEventListener
{
    private final ScoreboardService scoreboardService;

    public MyEventListener(ScoreboardService scoreboardService)
    {
        this.scoreboardService = scoreboardService;
    }

    @EventListener(MyEvent.class)
    public void listener(MyEvent myEvent)
    {
        this.scoreboardService.resetScoreboard();
        this.scoreboardService.populateScoreboard();
    }
}
