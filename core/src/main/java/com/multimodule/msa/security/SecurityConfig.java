package com.multimodule.msa.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 설정.
 *
 * @author always0ne
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
        .antMatchers(HttpMethod.GET, "/**").permitAll()
        .anyRequest().hasRole("USER")
        .and()
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
