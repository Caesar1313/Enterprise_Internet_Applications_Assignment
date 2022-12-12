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
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@Order(1)
//public class DigestSecurityConfig {
//
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                .password(passwordEncoder().encode("user1Pass"))
//                .roles("USER");
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/admin/**").
//                addFilter(getDigestAuthFilter()).exceptionHandling()
//                .authenticationEntryPoint(getDigestEntryPoint())
//                .and().authorizeRequests().antMatchers("/admin/**").hasRole("USER");
//    }
//
//    private DigestAuthenticationEntryPoint getDigestEntryPoint() {
//        DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
//        digestEntryPoint.setRealmName("EYAD.COM");
//        digestEntryPoint.setKey("EAD");
//        digestEntryPoint.setNonceValiditySeconds(60);
//        return digestEntryPoint;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("user1Pass"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1);
//    }
//
//
//
//    private DigestAuthenticationFilter getDigestAuthFilter() throws Exception {
//        DigestAuthenticationFilter digestFilter = new DigestAuthenticationFilter();
//        digestFilter.setUserDetailsService(userDetailsService());
//        digestFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
//        return digestFilter;
//    }
//
//}
