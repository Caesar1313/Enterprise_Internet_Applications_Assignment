//package com.example.enterprise_internet_applications_project.configrations;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//@Order(1)
//public class DBSecurityConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource).passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("select name,password from person where name=?")
//                .authoritiesByUsernameQuery("select name,authority from authorities where name=?");
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/admin/gettestapi").hasAnyRole("USER", "ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .logout().permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/403");
//
//        http.csrf().disable();
//    }
//
//    private DigestAuthenticationEntryPoint getDigestEntryPoint() {
//        DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
//        digestEntryPoint.setRealmName("EIA.COM");
//        digestEntryPoint.setKey("EIA");
//        digestEntryPoint.setNonceValiditySeconds(60);
//        return digestEntryPoint;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//
//
//}
