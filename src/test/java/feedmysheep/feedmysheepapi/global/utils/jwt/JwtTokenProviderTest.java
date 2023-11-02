package feedmysheep.feedmysheepapi.global.utils.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JwtTokenProviderTest {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Test
  @DisplayName("[JWT토큰] 유효한 엑세스토큰 만들기")
  public void 유효한엑세스토큰만들기() {
    JwtDto.memberInfo testMemberInfo = {

    }
    String testAccessToken = this.jwtTokenProvider.createAccessToken()
  }
}