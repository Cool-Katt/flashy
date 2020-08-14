package sofuni.flashy.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sofuni.flashy.services.impl.StatsService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController
{
    private StatsService statsService;

    public AdminController(StatsService statsService)
    {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public String stats(Model model)
    {

        model.addAttribute("requestCount", this.statsService.getRequestCount());
        model.addAttribute("startedOn", this.statsService.getStartedOn());

        return "stats";
    }

    @GetMapping("/players")
    public String players(Model model)
    {
        model.addAttribute("players", this.statsService.showAllPlayers());
        return "players";
    }
}
