package pl.javastart.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/logout").permitAll()
                    .antMatchers("/dodaj").permitAll()
                    .antMatchers("/usun").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/profil").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/haslo").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .failureUrl("/error")
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, role FROM user_role WHERE username = ?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
