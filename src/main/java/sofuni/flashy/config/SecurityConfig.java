package sofuni.flashy.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.
                authorizeRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                antMatchers("/login**", "/login-error**", "/registration").permitAll().
                antMatchers("/**").permitAll();
                /*authenticated().
                and().
                formLogin().
                loginPage("/login").
                loginProcessingUrl("/login/authenticate").
                failureForwardUrl("/login-error").
                successForwardUrl("/home").
                and().
                logout().
                logoutUrl("/logout").
                logoutSuccessUrl("/login").
                invalidateHttpSession(true).
                deleteCookies("JSESSIONID");*/
    }
}
