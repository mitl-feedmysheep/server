package feedmysheep.feedmysheepapi.global.utils.jwt;

import feedmysheep.feedmysheepapi.TESTDATA;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import feedmysheep.feedmysheepapi.TESTDATA;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import static org.assertj.core.api.Assertions.*;


class JwtTokenProviderTest {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  private JwtDto.memberInfo memberInfo;

  JwtTokenProviderTest() {}

  @BeforeEach()
  public void setup() {
    memberInfo = new JwtDto.memberInfo();
    memberInfo.setMemberId(TESTDATA.memberId);
    memberInfo.setLevel(TESTDATA.level);
    memberInfo.setMemberName(TESTDATA.memberName);
  }

  @Test
  @DisplayName("[JWT토큰] 엑세스토큰 만들기")
  public void 엑세스토큰만들기() {
    // given
    // when
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    // then
    assertThat(accessToken).isNotNull();
  }

  @Test
  @DisplayName("[JWT토큰] 리프레시토큰 만들기")
  public void 리프레시토큰만들기() {
    // given
    // when
    String refreshToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    // then
    assertThat(refreshToken).isNotNull();
  }

  @Test
  @DisplayName("[JWT토큰] 토큰 검증하기")
  public void 토큰검증성공() {
    // given
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    // when
    JwtDto.memberInfo tokenInfo = this.jwtTokenProvider.validateToken(accessToken);

    // then
    assertThat(tokenInfo.getMemberId()).isEqualTo(TESTDATA.memberId);
    assertThat(tokenInfo.getLevel()).isEqualTo(TESTDATA.level);
    assertThat(tokenInfo.getMemberName()).isEqualTo(TESTDATA.memberName);
  }

  @Test
  @DisplayName("[JWT토큰] 잘못된 토큰")
  public void 토큰검증실패() {
    // given
    String invalidToken = "random.invalid.token";

    // when
    // then
    assertThatThrownBy(() -> this.jwtTokenProvider.validateToken(invalidToken)).hasMessageContaining(ErrorMessage.INVALID_JWT);
  }

  // TODO expiredToken은 어떻게 검증하지?
}