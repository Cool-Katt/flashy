package sofuni.flashy.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import sofuni.flashy.services.PlayerService;
import sofuni.flashy.services.impl.StatsService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController
{
    private final StatsService statsService;
    private final PlayerService playerService;

    public AdminController(StatsService statsService, PlayerService playerService)
    {
        this.statsService = statsService;
        this.playerService = playerService;
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

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute(name = "email") String deleteId)
    {
        this.playerService.deleteByEmail(deleteId);
        return "redirect:/admin/players";
    }
}
