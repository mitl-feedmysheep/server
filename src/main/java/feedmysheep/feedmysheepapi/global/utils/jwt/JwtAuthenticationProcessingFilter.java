package feedmysheep.feedmysheepapi.global.utils.jwt;

import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
  private static final List<String> BYPASS_URL_PATTERN =
      List.of(
          "/app/member/phone/send-verification-code",
          "/app/member/phone/check-verification-code",
          "/app/member/email/check-duplication",
          "/app/member/sign-up",
          "/app/member/sign-in",
          "/app/token"
      );
  private JwtTokenProvider jwtTokenProvider;
  private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  JwtAuthenticationProcessingFilter(JwtTokenProvider jwtTokenProvider, GrantedAuthoritiesMapper authoritiesMapper) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.authoritiesMapper = authoritiesMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String requestUri = request.getRequestURI();

    // Bypass 검증
    for (String urlPattern : BYPASS_URL_PATTERN) {
      if (requestUri.contains(urlPattern)) {
        filterChain.doFilter(request, response);
        return;
      }
    }

    // 토큰 여부 검증
    String accessToken = request.getHeader("fms-token");
    if (accessToken == null || accessToken.isEmpty()) {
     throw new CustomException(ErrorMessage.NO_TOKEN);
    }

    // 유효 토큰 검증
    JwtDto.memberInfo memberInfo = this.jwtTokenProvider.validateToken(accessToken);
    saveAuthentication();
    filterChain.doFilter(request, response);

  }

  private void saveAuthentication(JwtDto.memberInfo memberInfo) {
    // TODO 커스텀유저디테일 만드는 것 보기!
    CustomUserDetails user
  }
}
