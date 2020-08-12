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
import sofuni.flashy.services.impl.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl flashyUserDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailServiceImpl flashyUserDetailsService)
    {
        this.passwordEncoder = passwordEncoder;

        this.flashyUserDetailsService = flashyUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.
                authorizeRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                antMatchers("/login**", "/login-error**", "/registration").permitAll().
                antMatchers("/**").
                authenticated().
                and().
                formLogin().
                loginPage("/login").
                loginProcessingUrl("/login/authenticate").
                failureForwardUrl("/login-error").
                successForwardUrl("/index").
                and().
                logout().
                logoutUrl("/logout").
                logoutSuccessUrl("/login").
                invalidateHttpSession(true).
                deleteCookies("JSESSIONID");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {
        authManager.
                userDetailsService(flashyUserDetailsService).
                passwordEncoder(passwordEncoder);
    }
}
