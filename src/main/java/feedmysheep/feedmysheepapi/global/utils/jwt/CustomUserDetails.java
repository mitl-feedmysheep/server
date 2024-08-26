package feedmysheep.feedmysheepapi.global.utils.jwt;

import java.util.Collection;
import java.util.UUID;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

  private String username;
  private String password;
  private final boolean accountNonLocked = true;
  private final boolean accountNonExpired = true;
  private final boolean credentialsNonExpired = true;
  private final boolean enabled = true;
  private Collection<? extends GrantedAuthority> authorities;


  @Getter
  private final UUID memberId;
  @Getter
  private final String memberName;

  public CustomUserDetails(JwtDto.memberInfo memberInfo) {
    // 추가
    this.memberId = memberInfo.getMemberId();
    this.memberName = memberInfo.getMemberName();

  }
}
