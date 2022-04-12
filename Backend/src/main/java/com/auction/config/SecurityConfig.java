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

  private final String ADMIN = UserRole.ADMIN.name();
  private final String USER = UserRole.USER.name();

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
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
        .antMatchers(HttpMethod.DELETE, "/api/auction{auctionId}").hasAnyRole(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/{auctionId}").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/auction/{auctionId}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, "/api/auction/block/{auctionId}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/category/{subCategoryId}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auction/participant/{userId}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/owner/{userId}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/auction/all/{userId}").hasAnyAuthority(USER)

//        AuctionWinnerController
        .antMatchers(HttpMethod.GET, "/api/winner/user/{userId}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.GET, "/api/winner/creator/{userId}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/winner/address/default").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/winner/address").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.POST, "/api/winner/track/{trackNumber}").hasAnyAuthority(USER)
        .antMatchers(HttpMethod.PUT, "/api/winner/{auctionId}/delivered").hasAnyAuthority(USER)

//        AuthController
        .antMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
        .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
        .antMatchers(HttpMethod.POST, "/api/auth/confirm").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/token/{token}").permitAll()

//        CategoryController
        .antMatchers(HttpMethod.GET, "/api/category").permitAll()
        .antMatchers(HttpMethod.POST, "/api/category").hasAnyRole(ADMIN)

//        ComplaintController
        .antMatchers(HttpMethod.GET, "/api/complaint").hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.POST, "/api/complaint/answer").hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.POST, "/api/complaint").hasAnyRole(ADMIN)

//        ImageLinkController
        .antMatchers(HttpMethod.GET, "/api/links/{type}").permitAll()
        .antMatchers(HttpMethod.POST, "/api/complaint").hasAnyRole(ADMIN)

//        IoTController
        .antMatchers(HttpMethod.POST, "/api/iot/bet").permitAll()
        .antMatchers(HttpMethod.POST, "/api/iot/finish").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/iot/connect").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/iot/disconnect").permitAll()
        .antMatchers(HttpMethod.GET, "/api/iot/auction").permitAll()

//        PaymentController
        .antMatchers(HttpMethod.POST, "/api/payment/success").hasAnyRole(USER)
        .antMatchers(HttpMethod.GET, "/api/payment").hasAnyRole(USER)
        .antMatchers(HttpMethod.GET, "/api/payment/send/{userId}").hasAnyRole(USER)
        .antMatchers(HttpMethod.GET, "/api/payment/receive/{userId}").hasAnyRole(USER)

//        SettingController
        .antMatchers(HttpMethod.GET, "/api/settings/{userId}").hasAnyRole(USER)
        .antMatchers(HttpMethod.PUT, "/api/settings/notification").hasAnyRole(USER)

//        SettingController
        .antMatchers(HttpMethod.GET, "/api/statistic").hasAnyRole(ADMIN)


//        TokenController
        .antMatchers(HttpMethod.GET, "/api/token/refresh").permitAll()

//        UserController
        .antMatchers(HttpMethod.POST, "/api/user/reset/password/{email}").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/user/password").hasAnyRole(USER)
        .antMatchers(HttpMethod.PUT, "/api/user").hasAnyRole(USER)
        .antMatchers(HttpMethod.POST, "/api/user/update/password/{code}").permitAll()
        .antMatchers(HttpMethod.POST, "/api/user/disable/code/{code}").permitAll()
        .antMatchers(HttpMethod.POST, "/api/user/disable/{userId}").hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.POST, "/api/user/enable/{userId}").hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.GET, "/api/user").hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.GET, "/api/user/{userId}").hasAnyRole(USER)
        .antMatchers(HttpMethod.DELETE, "/api/user/{userId}").hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.PUT, "/api/user/{userId}/tutorial").hasAnyRole(USER)
        .antMatchers(HttpMethod.POST, "/api/user/address").hasAnyRole(USER)

//        AuctionChatController
        .antMatchers(HttpMethod.GET, "/api/auction/chat/{id}").permitAll()



        .antMatchers("/websocket/**").permitAll()
        .antMatchers("/websocket").permitAll()
        .antMatchers("/actuator").permitAll()
        .antMatchers("/actuator/*").permitAll()
        .antMatchers("/instances").permitAll()
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
