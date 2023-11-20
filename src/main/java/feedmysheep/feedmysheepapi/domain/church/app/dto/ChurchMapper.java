package feedmysheep.feedmysheepapi.domain.church.app.dto;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChurchMapper {

  List<ChurchResDto.getChurch> getChurchList(List<ChurchEntity> churchList);

  List<ChurchResDto.getBodyListByChurchId> getBodyListByChurchId(List<BodyEntity> bodyList);
}
