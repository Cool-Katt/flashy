package sofuni.flashy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sofuni.flashy.config.MyEventPublisher;
import sofuni.flashy.services.ScoreboardService;

@Controller
public class ScoreboardController
{
    private final MyEventPublisher myEventPublisher;
    private final ScoreboardService scoreboardService;

    public ScoreboardController(MyEventPublisher myEventPublisher, ScoreboardService scoreboardService)
    {
        this.myEventPublisher = myEventPublisher;
        this.scoreboardService = scoreboardService;
    }

    @GetMapping("/scoreboard")
    public String loadScoreboard(Model model)
    {
        this.myEventPublisher.publishEvent("Seeding the Scoreboard");
        model.addAttribute("scoreboard", this.scoreboardService.showScoreboard());
        return "score";
    }
}
