package com.auction.config;

import com.auction.config.jwt.AuthEntryPointJwt;
import com.auction.config.jwt.AuthTokenFilter;
import com.auction.config.jwt.JwtUtils;
import com.auction.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import org.springframework.security.core.userdetails.UserDetailsService;
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

  private static final String ADMIN = UserRole.ADMIN.name();
  private static final String USER = UserRole.USER.name();

  private final UserDetailsService userDetailsService;
  private final AuthEntryPointJwt unauthorizedHandler;
  private final JwtUtils jwtUtils;


  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter(jwtUtils, userDetailsService);
  }

  @Bean
  @Override
  @SneakyThrows
  public AuthenticationManager authenticationManagerBean() {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors().and()
        .httpBasic().disable()
        .csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()

//        AuctionActionController
        .antMatchers(HttpMethod.GET, "/api/bids/*").permitAll()
        .antMatchers(HttpMethod.GET, "/api/bids/last").permitAll()

//        AuctionEventController
        .antMatchers(HttpMethod.POST, "/api/auction").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auction/filter").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auction/all").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auction/sort").permitAll()
        .antMatchers(HttpMethod.DELETE, "/api/auction{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/{\\d+}").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/auction/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, "/api/auction/block/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/category/{\\d+}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auction/participant/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/owner/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/all/{\\d+}").hasAnyAuthority(USER)

//        AuctionWinnerController
        .antMatchers(HttpMethod.GET, "/api/winner/user/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/winner/creator/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/winner/address/default").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/winner/address").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/winner/track/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, "/api/winner/{\\d+}/delivered").hasAnyAuthority(USER)

//        AuthController
        .antMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
        .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
        .antMatchers(HttpMethod.POST, "/api/auth/confirm").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/token/{\\d+}").permitAll()

//        CategoryController
        .antMatchers(HttpMethod.GET, "/api/category").permitAll()
        .antMatchers(HttpMethod.POST, "/api/category").hasAnyAuthority(ADMIN)

//        ComplaintController
        .antMatchers(HttpMethod.GET, "/api/complaint").hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.POST, "/api/complaint").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/complaint/answer").hasAnyAuthority(ADMIN)

//        ImageLinkController
        .antMatchers(HttpMethod.GET, "/api/links/{\\d+}").permitAll()
        .antMatchers(HttpMethod.POST, "/api/links").hasAnyAuthority(ADMIN)

//        IoTController
        .antMatchers(HttpMethod.POST, "/api/iot/bet").permitAll()
        .antMatchers(HttpMethod.POST, "/api/iot/finish").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/iot/connect").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/iot/disconnect").permitAll()
        .antMatchers(HttpMethod.GET, "/api/iot/auction").permitAll()

//        PaymentController
        .antMatchers(HttpMethod.POST, "/api/payment/success").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/payment").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/payment/send/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/payment/receive/{\\d+}").hasAnyAuthority(USER)

//        SettingController
        .antMatchers(HttpMethod.GET, "/api/settings/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, "/api/settings/notification").hasAnyAuthority(USER)

//        SettingController
        .antMatchers(HttpMethod.GET, "/api/statistic").hasAnyAuthority(ADMIN)

//        TokenController
        .antMatchers(HttpMethod.GET, "/api/token/refresh").permitAll()

//        UserController
        .antMatchers(HttpMethod.POST, "/api/users/reset/password/{\\d+}").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/users/password").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, "/api/users/tutorial/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, "/api/users").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/users/update/password/{\\d+}").permitAll()
        .antMatchers(HttpMethod.POST, "/api/users/disable/code/{\\d+}").permitAll()
        .antMatchers(HttpMethod.POST, "/api/users/disable/{\\d+}").hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.POST, "/api/users/enable/{\\d+}").hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.GET, "/api/users").hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.GET, "/api/users/{\\d+}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.DELETE, "/api/users/{\\d+}").hasAnyAuthority(ADMIN)
        .antMatchers(HttpMethod.POST, "/api/users/address").hasAnyAuthority(USER)

//        AuctionChatController
        .antMatchers(HttpMethod.GET, "/api/auction/chat/{\\d+}").permitAll()

        .antMatchers("/websocket/**").permitAll()
        .antMatchers("/websocket").permitAll()
        .antMatchers("/actuator").permitAll()
        .antMatchers("/actuator/*").permitAll()
        .antMatchers("/instances").permitAll()
        .antMatchers("/api/instances/*").permitAll()
        .antMatchers("/wb/users").permitAll()

//        .antMatchers("/**").permitAll()
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
