package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ChurchService {

  private final ChurchRepository churchRepository;
  private final MemberRepository memberRepository;

  @Autowired
  public ChurchService(ChurchRepository churchRepository, MemberRepository memberRepository) {
    this.churchRepository = churchRepository;
    this.memberRepository = memberRepository;
  }

  public List<ChurchResDto.getChurchList> getChurchList(Authentication authentication) {
    // 1. 유효한 멤버인지 검사
    System.out.println("1" + authentication.getAuthorities());
    System.out.println("2" + authentication.getCredentials());
    System.out.println("3" + authentication.getDetails());
    System.out.println("4" + authentication.getPrincipal());
    System.out.println("5" + authentication.getName());
    System.out.println("6" + authentication.getClass());
//    MemberEntity isValidMember = this.memberRepository.existsMemberByMemberId()

    // 2. 교회 리스트 반환
    return this.churchRepository.getAllValidChurchList();
  }
}
