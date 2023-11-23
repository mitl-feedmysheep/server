package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberEntity extends CreatedUpdated {


  //  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id", nullable = false, columnDefinition = "bigint COMMENT '멤버 아이디'")
  private Long memberId;

  @Setter
  @Column(name = "authorization_id", nullable = false, columnDefinition = "bigint COMMENT '권한 아이디'")
  private Long authorizationId;

  @Setter
  @Column(name = "is_active", nullable = false, columnDefinition = "tinyint(1) NOT NULL COMMENT '유효 계정 여부'")
  private boolean isActive = true;

  @Column(name = "member_name", nullable = false, length = 10, columnDefinition = "varchar(10) COMMENT '멤버 이름'")
  private String memberName;

  @Column(name = "sex", nullable = false, length = 5, columnDefinition = "varchar(1) COMMENT '성별 (M / F)'")
  private String sex;

  @Setter
  @Column(name = "birthday", nullable = false, columnDefinition = "date COMMENT '멤버 생년월일'")
  private LocalDate birthday;

  @Setter
  @Column(name = "phone", nullable = false, length = 20, columnDefinition = "varchar(20) COMMENT '멤버 휴대폰번호'")
  private String phone;

  @Setter
  @Column(name = "profile_image_url", length = 200, columnDefinition = "varchar(200) COMMENT '멤버 프로필 이미지 URL'")
  private String profileImageUrl;

  @Setter
  @Column(name = "address", nullable = false, length = 100, columnDefinition = "varchar(100) COMMENT '멤버 주소'")
  private String address;

  @Setter
  @Column(name = "member_description", length = 100, columnDefinition = "varchar(100) COMMENT '멤버 특이사항'")
  private String memberDescription;

  @Column(name = "email", nullable = false, length = 100, columnDefinition = "varchar(100) COMMENT '멤버 로그인 이메일'")
  private String email;

  @Setter
  @Column(name = "password", nullable = false, length = 100, columnDefinition = "varchar(100) COMMENT '멤버 해싱된 비밀번호'")
  private String password;

  @Column(name = "registered_at", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '멤버 가입일시'")
  private LocalDateTime registeredAt = LocalDateTime.now();

  @Builder
  public MemberEntity(Long authorizationId, String memberName, String sex, LocalDate birthday,
      String phone,
      String address, String email, String password) {
    this.authorizationId = authorizationId;
    this.memberName = memberName;
    this.sex = sex;
    this.birthday = birthday;
    this.phone = phone;
    this.address = address;
    this.email = email;
    this.password = password;
  }
}