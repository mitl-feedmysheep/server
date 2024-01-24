package feedmysheep.feedmysheepapi.domain.cell.app.cellService;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellMapper;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCell;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellRepository;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellService {

  private final CellRepository cellRepository;
  private CellService cellService;
  private CellMapper cellMapper;
  private CellMemberMapRepository cellMemberMapRepository;


  @Autowired
  public CellService(CellRepository cellRepository, CellMapper cellMapper, CellMemberMapRepository cellMemberMapRepository) {
    this.cellRepository = cellRepository;
    this.cellMapper = cellMapper;
    this.cellMemberMapRepository = cellMemberMapRepository;
  }


  public List<getCell> getCellList(Long cellId, CustomUserDetails customUserDetails) {

    // 1. cell 조회하기 (memberId를 통하여, cellId 가져오기)
    List<CellMemberMapEntity> cellListByMemberId = this.cellMemberMapRepository.getCellMemberMapListByMemberId(
        customUserDetails.getMemberId());

    // 2. 조회한 cellId값들을 List로 만들기
    List<Long> cellIdListByMemberId = cellListByMemberId.stream().map(CellMemberMapEntity::getMemberId).toList();

    // 3. cellId가 없는 경우 처리
    if (cellIdListByMemberId.isEmpty()) {
      throw new CustomException(ErrorMessage.NO_CELL_FOUND);
    }

    // 4. cellId가 있는 경우 처리
    List<CellEntity> cellList = this.cellRepository.getCellList(cellIdListByMemberId);

    // 5. 반환
    return this.cellMapper.getCellList(cellList);

  }
}
