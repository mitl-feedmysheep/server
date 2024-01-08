package feedmysheep.feedmysheepapi.global.utils.jwt;

import java.util.Collection;
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


  private final Long memberId;
  private final String memberName;

  public CustomUserDetails(JwtDto.memberInfo memberInfo) {
    // 추가
    this.memberId = memberInfo.getMemberId();
    this.memberName = memberInfo.getMemberName();
  }

  public Long getMemerId() {
    return this.memberId;
  }
  
  public String getMemberName() {
    return this.memberName;
  }
}
