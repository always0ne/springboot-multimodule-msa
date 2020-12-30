package com.restapi.template.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.template.api.common.response.ErrorResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * JWT 인증 필터.
 *
 * @author always0ne
 * @version 1.0
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;
  private final ObjectMapper objectMapper;

  /**
   * JWT 토큰 검증.
   * 만료된 토큰이 발견되었을 때, 만료된 토큰 응답 발생
   *
   * @param request     SubletRequest
   * @param response    SubletResponse
   * @param filterChain FilterChain
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      Claims claims = jwtTokenProvider.resolveToken((HttpServletRequest) request);
      if (claims != null) {
        SecurityContextHolder.getContext()
            .setAuthentication(jwtTokenProvider.getAuthentication(claims));
      }
      filterChain.doFilter(request, response);
    } catch (SignatureException e) {
      sendErrorMessage((HttpServletResponse) response, "0003", "유효하지 않은 토큰입니다.");
    } catch (MalformedJwtException e) {
      sendErrorMessage((HttpServletResponse) response, "0004", "손상된 토큰입니다.");
    } catch (DecodingException e) {
      sendErrorMessage((HttpServletResponse) response, "0005", "잘못된 인증입니다.");
    } catch (ExpiredJwtException e) {
      sendErrorMessage((HttpServletResponse) response, "0006", "만료된 토큰입니다.");
    }
  }

  /**
   * JWT 토큰 검증.
   * 만료된 토큰이 발견되었을 때, 만료된 토큰 응답 발생
   *
   * @param res     response 객체
   * @param error   오류 번호
   * @param message 메시지
   * @throws IOException String으로 변환하는중 발생할 수 있는 오류
   */
  private void sendErrorMessage(HttpServletResponse res, String error, String message)
      throws IOException {
    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
    res.setContentType(MediaType.APPLICATION_JSON.toString());
    res.getWriter().write(this.objectMapper
        .writeValueAsString(new ErrorResponse(HttpStatus.FORBIDDEN, error, message)));

  }
}
