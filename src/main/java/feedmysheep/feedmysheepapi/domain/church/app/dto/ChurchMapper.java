package feedmysheep.feedmysheepapi.domain.church.app.dto;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ChurchMapper {


  List<ChurchResDto.getChurch> getChurchList(List<ChurchEntity> churchList);

  List<ChurchResDto.getBodyByChurchId> getBodyListByChurchId(List<BodyEntity> bodyList);

  List<ChurchServiceDto.memberEventList> setMemberEventList(List<MemberEntity> memberList);

  ChurchResDto.getMemberEventListByMemberId getMemberEventsByBodyId(int totalMemberEventCount,
      List<ChurchServiceDto.memberEventList> memberEventList);
}
