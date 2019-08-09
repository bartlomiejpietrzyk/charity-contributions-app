package pl.coderslab.charity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.coderslab.charity.service.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SecurityConfig(AccessDeniedHandler accessDeniedHandler,
                          AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/registration**", "/user/*/enable", "/403", "/404", "/500").permitAll()
                .antMatchers("/user**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin**").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/img/**",
                        "/scss/**",
                        "/vendor/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }
}