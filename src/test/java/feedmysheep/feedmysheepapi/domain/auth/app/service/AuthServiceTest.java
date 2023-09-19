package feedmysheep.feedmysheepapi.domain.auth.app.service;

import static org.assertj.core.api.Assertions.*;

import feedmysheep.feedmysheepapi.TESTDATA;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthReqDto.createToken;
import feedmysheep.feedmysheepapi.domain.auth.app.dto.AuthResDto;
import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto.signUp;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.member.app.service.MemberService;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//@DataJpaTest
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AuthServiceTest {
  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private AuthorizationRepository authorizationRepository;

  @Autowired
  private AuthService authService;

  @Autowired
  private MemberService memberService;

  private MemberResDto.signUp tokens;

  @BeforeEach
  public void beforeEach() {
    MemberReqDto.signUp body = new signUp();
    body.setMemberName(TESTDATA.memberName);
    body.setSex(TESTDATA.sex);
    body.setBirthday(TESTDATA.birthday);
    body.setPhone(TESTDATA.phone);
    body.setEmail(TESTDATA.email);
    body.setPassword(TESTDATA.password);
    body.setPhone(TESTDATA.phone);
    body.setAddress(TESTDATA.address);
    this.tokens = this.memberService.signUp(body);
  }

  @AfterEach
  public void afterEach() {
    this.memberRepository.deleteAll();
  }

  @Test
  @DisplayName("[토큰재발급] 재발급된 토큰이 유효한지 검사")
  public void 토큰재발급() throws InterruptedException {
    // given
    AuthReqDto.createToken token = new createToken(this.tokens.getRefreshToken());
    Thread.sleep(1000);

    // when
    AuthResDto.createToken tokenSet = this.authService.createToken(token);

    // then
    assertThat(tokenSet.getRefreshToken()).isNotEqualTo(this.tokens.getRefreshToken());

  }
}