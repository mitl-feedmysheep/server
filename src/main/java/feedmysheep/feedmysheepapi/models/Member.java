package feedmysheep.feedmysheepapi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id", nullable = false, columnDefinition = "bigint COMMENT '멤버 아이디'")
  private Long memberId;

  @OneToOne
  @JoinColumn(name = "authorization_id")
  private Authorization authorization;

  @OneToMany(mappedBy = "member")
  private List<ChurchMemberMap> churchMemberList = new ArrayList<>();

  @Column(name = "member_name", nullable = false, length = 10, columnDefinition = "varchar(10) COMMENT '멤버 이름'")
  private String memberName;

  @Column(name = "sex", nullable = false, length = 1, columnDefinition = "varchar(1) COMMENT '성별 (M / F)'")
  private String sex;

  @Column(name = "birthday", nullable = false, columnDefinition = "date COMMENT '멤버 생년월일'")
  private Date birthday;

  @Column(name = "phone", nullable = false, length = 20, columnDefinition = "varchar(20) COMMENT '멤버 휴대폰번호'")
  private String phone;

  @Column(name = "profile_image_url", length = 200, columnDefinition = "varchar(200) COMMENT '멤버 프로필 이미지 URL'")
  private String profileImageUrl;

  @Column(name = "address", length = 100, columnDefinition = "varchar(100) COMMENT '멤버 주소'")
  private String address;

  @Column(name = "member_description", length = 100, columnDefinition = "varchar(100) COMMENT '멤버 특이사항'")
  private String memberDescription;

  @Column(name = "millitary_service", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 COMMENT '멤버 복무중 여부'")
  private boolean militaryService;

  @Column(name = "studying_abroad", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0 COMMENT '멤버 유학중 여부'")
  private boolean studyingAbroad;

  @Column(name = "email", length = 100, columnDefinition = "varchar(100) COMMENT '멤버 로그인 이메일'")
  private String email;

  @Column(name = "registered_at", columnDefinition = "datetime COMMENT '멤버 가입일시'")
  private LocalDateTime registeredAt;
}
