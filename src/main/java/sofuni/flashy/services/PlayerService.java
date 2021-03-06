package sofuni.flashy.services;

import sofuni.flashy.models.serviceModels.PlayerServiceModel;

public interface PlayerService
{
    void init();

    PlayerServiceModel registerPlayer(PlayerServiceModel playerServiceModel);

    PlayerServiceModel loginPlayer(PlayerServiceModel playerServiceModel);

    PlayerServiceModel findPlayer(String email);

    void deleteByEmail(String email);
}
