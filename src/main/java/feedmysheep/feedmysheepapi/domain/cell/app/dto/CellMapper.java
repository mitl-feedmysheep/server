package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCell;
import feedmysheep.feedmysheepapi.models.CellEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CellMapper {

  List<getCell> getCellList(List<CellEntity> cellList);

};

