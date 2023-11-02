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
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  // 비밀번호 암호화
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().disable()			//cors 방지
        .csrf().disable()			//csrf 방지
        .formLogin().disable()		//기본 로그인페이지 없애기
        .headers().frameOptions().disable();

    http.addFilterAfter(new JwtAuthenticationProcessingFilter(jwtService, userServiceImpl), LogoutFilter.class);

    return http.build();
  }

  // JWT
  @Bean
  public JwtTokenProvider jwtTokenProvider() {
    return new JwtTokenProvider();
  }
}
