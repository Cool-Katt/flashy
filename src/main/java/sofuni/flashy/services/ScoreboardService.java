package sofuni.flashy.services;

import sofuni.flashy.models.serviceModels.ScoreboardServiceModel;

import java.util.List;

public interface ScoreboardService
{
    List<ScoreboardServiceModel> showScoreboard();

    void populateScoreboard();

    void resetScoreboard();
}
