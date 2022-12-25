package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import security.catchLoginAndRegister;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    catchLoginAndRegister authentication;
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/v3/api-docs"
                                                            ,"/swagger-ui/**"
                                                            ,"/rAndL/login"
                                                            ,"/rAndL/register"
                                                            ,"/websocket/**"
                                                            ,"/fakeTerminal/**").anonymous()
                .anyRequest().authenticated()
                .and().addFilterBefore(authentication, UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                .headers().frameOptions().sameOrigin();



    }


}
