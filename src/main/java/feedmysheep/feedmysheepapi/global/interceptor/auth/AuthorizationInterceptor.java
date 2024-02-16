package feedmysheep.feedmysheepapi.global.interceptor.auth;

import static feedmysheep.feedmysheepapi.global.policy.CONSTANT.JWT.TOKEN;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberRepository memberRepository;
  private final AuthorizationRepository authorizationRepository;


  @Autowired
  public AuthorizationInterceptor(MemberRepository memberRepository,
      AuthorizationRepository authorizationRepository, JwtTokenProvider jwtTokenProvider) {
    this.memberRepository = memberRepository;
    this.authorizationRepository = authorizationRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;

    AuthRequired auth = handlerMethod.getMethodAnnotation(AuthRequired.class);
    if (auth == null) {
      return true;
    }
    int authLevel = auth.value().getValue();

    String accessToken = request.getHeader(TOKEN); // doFilterInternal 이후에 호출 됨
    JwtDto.memberInfo memberInfo = this.jwtTokenProvider.validateToken(accessToken);
    Long memberId = memberInfo.getMemberId();
    MemberEntity member = this.memberRepository.findByMemberId(memberId)
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
    AuthorizationEntity authorization = this.authorizationRepository.getByAuthorizationId(
            member.getAuthorizationId())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_USER_AUTHORIZATION));

    if (authorization.getLevel() < authLevel) {
      throw new CustomException(ErrorMessage.NOT_AUTHORIZED);
    }

    return true;
  }
}
