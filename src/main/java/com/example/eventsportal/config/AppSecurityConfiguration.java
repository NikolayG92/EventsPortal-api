package com.example.eventsportal.config;



import com.example.eventsportal.config.jwt.JwtAuthenticationEntryPoint;
import com.example.eventsportal.config.jwt.JwtTokenVerifier;
import com.example.eventsportal.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenVerifier jwtTokenVerifier;


    public AppSecurityConfiguration(PasswordEncoder passwordEncoder, UserService userService,
                                    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                    JwtTokenVerifier jwtTokenVerifier) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/api/users/login", "/api/users/register").permitAll()
                .mvcMatchers("/swagger-ui.html/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenVerifier, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.passwordEncoder);
        provider.setUserDetailsService(this.userService);
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}