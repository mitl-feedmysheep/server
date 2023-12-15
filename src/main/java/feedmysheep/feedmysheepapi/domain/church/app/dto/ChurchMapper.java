package feedmysheep.feedmysheepapi.domain.church.app.dto;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChurchMapper {


  List<ChurchResDto.getChurch> getChurchList(List<ChurchEntity> churchList);

  List<ChurchResDto.getBodyByChurchId> getBodyListByChurchId(List<BodyEntity> bodyList);

  List<ChurchResDto.getMemberEventByMemberId> getMemberEventsByBodyId(
      List<MemberEntity> memberId);

}
