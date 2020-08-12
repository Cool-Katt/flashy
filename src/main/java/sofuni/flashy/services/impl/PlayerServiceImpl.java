package sofuni.flashy.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sofuni.flashy.models.entities.PlayerEntity;
import sofuni.flashy.models.entities.RoleEntity;
import sofuni.flashy.models.serviceModels.PlayerServiceModel;
import sofuni.flashy.repositories.PlayerRepository;
import sofuni.flashy.services.PlayerService;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService
{
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder)
    {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void init()
    {
        if (this.playerRepository.count() == 0)
        {
            PlayerEntity admin = new PlayerEntity();
            admin.setEmail("admin@example.com");
            admin.setPasswordHash(this.passwordEncoder.encode("topSecret"));

            RoleEntity roleAdmin = new RoleEntity("ROLE_ADMIN");
            RoleEntity roleUser = new RoleEntity("ROLE_USER");
            admin.setRoles(List.of(roleAdmin, roleUser));

            this.playerRepository.saveAndFlush(admin);
        }
    }
}
