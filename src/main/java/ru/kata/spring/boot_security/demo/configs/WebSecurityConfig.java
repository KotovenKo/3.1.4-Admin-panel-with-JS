package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.Service.UserSecurityService;

@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    @Autowired
    private UserSecurityService userSecurityService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
////                .antMatchers("/").permitAll()
////                .antMatchers("/admin/**").hasRole("ADMIN")
////                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/**").permitAll()
       http.csrf().disable()
               .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index/**").hasAnyRole("ADMIN", "USER")


                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userSecurityService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // аутентификация inMemory
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("admin")
//                        .roles("ADMIN")
//                        .build();
//
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}