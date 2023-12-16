package feedmysheep.feedmysheepapi.global.config;

import feedmysheep.feedmysheepapi.global.utils.jwt.JwtAuthenticationProcessingFilter;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig {

  // 비밀번호 암호화
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // JWT
  @Bean(name = "jwtTokenProvider")
  public JwtTokenProvider jwtTokenProvider() {
    return new JwtTokenProvider();
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().disable().csrf().disable().formLogin().disable().headers().frameOptions().disable();

//    http.addFilterAfter(new JwtAuthenticationProcessingFilter(new JwtTokenProvider(), new NullAuthoritiesMapper()), LogoutFilter.class);
    http.addFilterBefore(
        new JwtAuthenticationProcessingFilter(),
        UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
