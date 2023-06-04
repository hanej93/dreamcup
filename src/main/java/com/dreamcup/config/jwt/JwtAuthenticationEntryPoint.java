package com.dreamcup.config.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
      // todo 유효한 자격증명을 제공하지 않고 접근하려 할때 401
      log.debug("JwtAuthenticationEntryPoint.commence");
      // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
   }

}
