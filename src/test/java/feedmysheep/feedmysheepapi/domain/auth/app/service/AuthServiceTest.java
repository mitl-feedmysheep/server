//package feedmysheep.feedmysheepapi.domain.auth.app.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.inOrder;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import feedmysheep.feedmysheepapi.TESTDATA;
//import feedmysheep.feedmysheepapi.domain.DataFactory;
//import feedmysheep.feedmysheepapi.domain.TestUtil;
//import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
//import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto.createToken;
//import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
//import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
//import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
//import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto.signUp;
//import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
//import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
//import feedmysheep.feedmysheepapi.domain.member.app.service.MemberService;
//import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
//import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
//import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
//import feedmysheep.feedmysheepapi.models.MemberEntity;
//import java.util.Optional;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InOrder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//class AuthServiceTest {
//
//  @Mock
//  private MemberRepository memberRepository;
//
//  @Mock
//  private JwtTokenProvider jwtTokenProvider;
//
//  @Mock
//  private AuthorizationRepository authorizationRepository;
//
//  @Mock
//  private MemberService memberService;
//
//  @InjectMocks
//  private AuthService authService;
//
//  private static AuthReqDto.createToken body;
//  private static JwtDto.memberInfo memberInfo;
//  private static AuthorizationEntity authorization;
//  private static MemberEntity member;
//  @BeforeAll
//  static void setUp() {
//    body = new AuthReqDto.createToken();
//    memberInfo = new JwtDto.memberInfo();
//    authorization = DataFactory.createAuthorization();
//    member = DataFactory.createMember(authorization.getAuthorizationId());
//  }
//
//  @Test
//  @DisplayName("유효한 리프레시 토큰 -> 권한 업데이트 -> 토큰 재발급 성공")
//  public void test1() {
//    // given
//    when(this.jwtTokenProvider.validateToken(body.getRefreshToken())).thenReturn(memberInfo);
//    when(this.memberRepository.getMemberByMemberId(memberInfo.getMemberId())).thenReturn(
//        Optional.ofNullable(member));
//    when(this.authorizationRepository.getAuthorizationByAuthorizationId(authorization.getAuthorizationId())).thenReturn(Optional.ofNullable(authorization));
//    when(this.jwtTokenProvider.createRefreshToken(memberInfo)).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJOYW1lIjoi7YWM7Iqk7Yq4IiwibWVtYmVySWQiOjAsImlhdCI6MTcwNTIzMjYxMSwiZXhwIjo0ODU4ODMyNjExfQ.zpJ-bjrEtpCOMNiSjTSlSOhmU6GBPqARVyw2kCQcgqc");
//    when(this.jwtTokenProvider.createAccessToken(memberInfo)).thenReturn(TestUtil.getRandomString());
//
//    // when
//    AuthResDto.createToken ret = this.authService.createToken(body);
//
//    // then
//    verify(jwtTokenProvider, times(1)).validateToken(body.getRefreshToken());
//    verify(memberRepository, times(1)).getMemberByMemberId(memberInfo.getMemberId());
//    verify(authorizationRepository, times(1)).getAuthorizationByAuthorizationId(authorization.getAuthorizationId());
//    verify(jwtTokenProvider, times(1)).createRefreshToken(memberInfo);
//    verify(jwtTokenProvider, times(1)).createAccessToken(memberInfo);
//
//    InOrder inOrder = inOrder(jwtTokenProvider, memberRepository, authorizationRepository);
//    inOrder.verify(jwtTokenProvider).validateToken(body.getRefreshToken());
//    inOrder.verify(memberRepository).getMemberByMemberId(memberInfo.getMemberId());
//    inOrder.verify(authorizationRepository).getAuthorizationByAuthorizationId(authorization.getAuthorizationId());
//
//    assertThat(ret.getRefreshToken()).isNotNull();
//    assertThat(ret.getAccessToken()).isNotNull();
//  }
//}