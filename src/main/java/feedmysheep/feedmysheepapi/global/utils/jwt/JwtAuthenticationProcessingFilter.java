package feedmysheep.feedmysheepapi.global.utils.jwt;

import static feedmysheep.feedmysheepapi.global.policy.CONSTANT.JWT.TOKEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

  private static final List<String> BYPASS_URL_PATTERN = List.of("/data-seeding", // 초기 데이터 셋팅
      "/app/word", // 말씀 가져오기
      "/app/member/phone/send-verification-code", // 휴대폰 인증 번호 전송
      "/app/member/phone/check-verification-code", // 휴대폰 인증 코드 검사
      "/app/member/email/check-duplication", // 이메일 중복체크
      "/app/member/sign-up", // 회원가입
      "/app/member/sign-in", // 로그인
      "/app/auth/token", // 토큰 재발급
      "/app/member/find-email", // 이메일 찾기
      "/app/member/request-temporary-password" // 멤버 임시 비밀번호 요청
  );

  private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
  private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String requestUri = request.getRequestURI();

      // Bypass 검증
      for (String urlPattern : BYPASS_URL_PATTERN) {
        if (requestUri.contains(urlPattern)) {
          filterChain.doFilter(request, response);
          return;
        }
      }

      // 토큰 여부 검증
      String accessToken = request.getHeader(TOKEN);
      if (accessToken == null || accessToken.isEmpty()) {
        throw new CustomException(ErrorMessage.NO_TOKEN);
      }

      // 유효 토큰 검증
      JwtDto.memberInfo memberInfo = this.jwtTokenProvider.validateToken(accessToken);
      saveAuthentication(memberInfo);
      filterChain.doFilter(request, response);
    } catch (CustomException ex) {
      // 객체 준비
      response.setContentType("application/json;charset=UTF-8");
      PrintWriter writer = response.getWriter();
      Map<String, String> error = new HashMap<>();
      error.put("status", "fail");
      error.put("message", ex.getMessage());
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonAsString = objectMapper.writeValueAsString(error);
      // Servlet Response 리턴 (Global AOP에 잡히지 않는 필터)
      writer.print(jsonAsString);
      writer.flush();
    }

  }

  private void saveAuthentication(JwtDto.memberInfo memberInfo) {
    CustomUserDetails customUserDetails = new CustomUserDetails(memberInfo);

    Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null,
        authoritiesMapper.mapAuthorities(customUserDetails.getAuthorities()));

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
  }
}
