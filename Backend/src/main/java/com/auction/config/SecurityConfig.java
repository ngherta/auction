package com.auction.config;

import com.auction.config.jwt.AuthEntryPointJwt;
import com.auction.config.jwt.AuthTokenFilter;
import com.auction.config.services.UserDetailsServiceImpl;
import com.auction.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
         securedEnabled = true,
         jsr250Enabled = true,
        prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String ADMIN = UserRole.ADMIN.name();
    private final String USER = UserRole.USER.name();

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(false);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/auth/confirm").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/auction").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/auction/*").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/auction/sort").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/auction").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/api/auction/*").hasAuthority(USER)
//                .antMatchers(HttpMethod.PUT, "/api/auction/*").hasAuthority(USER)
//                .antMatchers(HttpMethod.PUT, "/api/auction/block/*").hasAuthority(USER)
//                .antMatchers(HttpMethod.GET, "/api/complaint").hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.POST, "/api/complaint/answer").hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.POST, "/api/complaint").hasAuthority(USER)
//                .antMatchers(HttpMethod.POST, "/api/users/reset/password").hasAuthority(USER)
//                .antMatchers(HttpMethod.POST, "/api/users/reset/password/confirm").hasAuthority(USER)
//                .antMatchers(HttpMethod.POST, "/api/users/update/password").hasAuthority(USER)
//                .antMatchers(HttpMethod.POST, "/api/users/disable/{userId}").hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.POST, "/api/users/enable/{userId}").hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.GET, "/api/users/{userId}").hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.GET, "/api/users").hasAuthority(USER)
//                .antMatchers(HttpMethod.DELETE, "/api/users").hasAuthority(USER)
                .antMatchers("/websocket/**").permitAll()
                .antMatchers("/websocket").permitAll()
                .antMatchers("/api/actuator").permitAll()
                .antMatchers("/api/actuator/*").permitAll()
                .antMatchers("/api/instances").permitAll()
                .antMatchers("/api/instances/*").permitAll()
                .antMatchers("/wb/users").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/instances",
                "/actuator/**"
        );
    }
}
