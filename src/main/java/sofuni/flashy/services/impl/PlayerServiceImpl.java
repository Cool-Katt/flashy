package sofuni.flashy.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sofuni.flashy.models.entities.PlayerEntity;
import sofuni.flashy.models.entities.RoleEntity;
import sofuni.flashy.models.serviceModels.PlayerServiceModel;
import sofuni.flashy.repositories.PlayerRepository;
import sofuni.flashy.services.PlayerService;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService
{
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl flashyDetailService;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper,
                             PasswordEncoder passwordEncoder, UserDetailServiceImpl flashyDetailService)
    {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.flashyDetailService = flashyDetailService;
    }

    @Override
    public PlayerServiceModel registerPlayer(PlayerServiceModel playerServiceModel)
    {
        PlayerEntity playerEntity = this.modelMapper.map(playerServiceModel, PlayerEntity.class);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("ROLE_USER");

        playerEntity.setRoles(List.of(roleEntity));
        return this.modelMapper.map(this.playerRepository.saveAndFlush(playerEntity), PlayerServiceModel.class);
    }

    @Override
    public PlayerServiceModel loginPlayer(PlayerServiceModel playerServiceModel)
    {
        Optional<PlayerEntity> playerEntityOptional = this.playerRepository.findByEmail(playerServiceModel.getEmail());
        if (playerEntityOptional.isPresent())
        {
            UserDetails userDetails =this.flashyDetailService.loadUserByUsername(playerServiceModel.getEmail());
            Authentication authentication =new UsernamePasswordAuthenticationToken(userDetails,
                    playerServiceModel.getPasswordHash(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return this.modelMapper.map(playerEntityOptional, PlayerServiceModel.class);
        } else
        {
            return null;
        }
    }

    @Override
    public PlayerServiceModel findPlayer(String email)
    {
        return this.playerRepository.findByEmail(email)
                .map(playerEntity -> this.modelMapper.map(playerEntity, PlayerServiceModel.class)).orElse(null);
    }

    @Override
    public void init()
    {
        if (this.playerRepository.count() == 0)
        {
            PlayerEntity admin = new PlayerEntity();
            admin.setEmail("admin@example.com");
            admin.setPasswordHash(this.passwordEncoder.encode("topSecret"));

            RoleEntity roleAdmin = new RoleEntity();
            roleAdmin.setRole("ROLE_ADMIN");
            RoleEntity roleUser = new RoleEntity();
            roleUser.setRole("ROLE_USER");
            admin.setRoles(List.of(roleAdmin, roleUser));

            this.playerRepository.saveAndFlush(admin);
        }
    }
}
