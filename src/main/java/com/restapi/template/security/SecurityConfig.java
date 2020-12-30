package com.restapi.template.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정.
 *
 * @author always0ne
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final ObjectMapper objectMapper;

  /**
   * Spring Security 설정.
   * URL, 메소드별 접근권한 설정
   * JWT 인증 필터 추가
   *
   * @param http HttpSecurity
   * @see "JwtAuthenticationFilter"
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable()
        .cors().and()
        .formLogin().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/auth/*").permitAll()
        .antMatchers(HttpMethod.GET, "/mustSignIn").hasRole("USER")
        .antMatchers(HttpMethod.GET, "/**").permitAll()
        .anyRequest().hasRole("USER")
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, objectMapper),
            UsernamePasswordAuthenticationFilter.class)
    ;
  }

  /**
   * PasswordEncoder Bean.
   *
   * @return PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
