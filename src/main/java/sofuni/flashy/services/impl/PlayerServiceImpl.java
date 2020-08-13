package sofuni.flashy.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sofuni.flashy.models.entities.PlayerEntity;
import sofuni.flashy.models.entities.RoleEntity;
import sofuni.flashy.models.serviceModels.PlayerServiceModel;
import sofuni.flashy.repositories.PlayerRepository;
import sofuni.flashy.services.PlayerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService, UserDetailsService
{
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper,
                             PasswordEncoder passwordEncoder)
    {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
            UserDetails userDetails =this.loadUserByUsername(playerServiceModel.getEmail());
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<PlayerEntity> playerEntity = this.playerRepository.findByEmail(username);
        return playerEntity.map(this::map).orElseThrow(() -> new UsernameNotFoundException("No such user: " + username));
    }

    private User map(PlayerEntity playerEntity) {
        List<GrantedAuthority> authorities = playerEntity.
                getRoles().
                stream().
                map(r -> new SimpleGrantedAuthority(r.getRole())).
                collect(Collectors.toList());

        return new User(
                playerEntity.getEmail(),
                playerEntity.getPasswordHash(),
                authorities);
    }

    @Override
    public boolean findPlayer(String email)
    {
        return this.playerRepository.findByEmail(email).isPresent();
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
