package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto.getChurchList;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ChurchService {

  private final ChurchRepository churchRepository;
  private final MemberRepository memberRepository;

  @Autowired
  public ChurchService(ChurchRepository churchRepository, MemberRepository memberRepository) {
    this.churchRepository = churchRepository;
    this.memberRepository = memberRepository;
  }


  //Boolean타입을 설정해줘서, 만약 멤버가 memberRepository저장소에 있다면,
  //existsMemberByMemberId의 파라미터(?)를 통해서 customUserDeatils객체에서
  // getMemberId를 받아와서, 해당 id를 가진 멤버가 저장소에 존재하는지 여부를 확인함.

  public List<ChurchResDto.getChurchList> getChurchList(
      CustomUserDetails customUserDetails) {
    // 1. 유효한 멤버인지 검사
    Boolean isValidMember = this.memberRepository.existsMemberByMemberId(
        customUserDetails.getMemberId());

    if (!isValidMember) {
      throw new CustomException(ErrorMessage.NO_AUTHORIZATION);
    }

    // 2. 교회 리스트 반환
    return this.churchRepository.getAllValidChurchList();
  }

  public List<ChurchResDto.getChurchList> registerChurch(ChurchResDto.getChurchList body){
    String churchName = body.getChurchName();
    String churchLocation = body.getChurchLocation();

    saveChurchInfo(churchName, churchLocation);

    List<ChurchResDto.getChurchList> churchList = churchRepository.getAllValidChurchList();

    return churchList;
  }

  //  * 방향: 해당 교회 정보를 담을 변수 및 객체를 생성하고, DB에 저장하기.
  //  * 문제점 : ChurchEntity에 접근을 하고싶은데, ChurchId랑 ChurchLocation이 private로 되어있어서 그런지,
  //          Protected 되어있다고 접근 안 됨.
  public void saveChurchInfo(String churchName, String churchLocation) {
    ChurchEntity churchEntity = new ChurchEntity();
    churchEntity.setChurchName(churchName);
    churchEntity.setChurchLocation(churchLocation);

    churchRepository.save(churchEntity);

  }
}
