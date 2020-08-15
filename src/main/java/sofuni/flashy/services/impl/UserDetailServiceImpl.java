package sofuni.flashy.services.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sofuni.flashy.models.entities.PlayerEntity;
import sofuni.flashy.repositories.PlayerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    private final PlayerRepository playerRepository;

    public UserDetailServiceImpl(PlayerRepository playerRepository)
    {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<PlayerEntity> playerEntity = this.playerRepository.findByEmail(username);
        return playerEntity.map(this::map).orElseThrow(() -> new UsernameNotFoundException("No such user: " + username));
    }

    private User map(PlayerEntity playerEntity)
    {
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
}
