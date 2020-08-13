package sofuni.flashy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import sofuni.flashy.services.impl.PlayerServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final PasswordEncoder passwordEncoder;
    private final PlayerServiceImpl playerService;

    public SecurityConfig(PasswordEncoder passwordEncoder, PlayerServiceImpl playerService)
    {
        this.passwordEncoder = passwordEncoder;
        this.playerService = playerService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.
                authorizeRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                antMatchers("/login**", "/login-error**", "/registration").permitAll().
                antMatchers("/**").authenticated().
                and().
                formLogin().
                loginPage("/login").
                loginProcessingUrl("/login/authenticate").
                failureForwardUrl("/login-error").successForwardUrl("/index").
                and().
                logout().
                //TODO: logoutSuccessUrl("/logout") put action in html
                logoutSuccessUrl("/login?logout").
                invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {
        authManager.
                userDetailsService(this.playerService).
                passwordEncoder(this.passwordEncoder);
    }
}
