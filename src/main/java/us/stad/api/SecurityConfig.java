package us.stad.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
        .withUser("user1").password("password1").roles("USER");
  }

  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().authorizeRequests().requestMatchers(new AntPathRequestMatcher("/employees/**", "DELETE"))
        .hasRole("USER").and().csrf().disable().headers().frameOptions().disable();
  }

}