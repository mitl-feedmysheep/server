package feedmysheep.feedmysheepapi.domain.member.app.dto;

import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  List<MemberResDto.getChurchWithBody> getMemberChurchesWithBodies(List<ChurchEntity> churchList);
}
