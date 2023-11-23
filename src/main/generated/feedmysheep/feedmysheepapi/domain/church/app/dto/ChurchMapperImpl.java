package feedmysheep.feedmysheepapi.domain.church.app.dto;

import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-22T13:49:37+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (JetBrains s.r.o.)"
)
@Component
public class ChurchMapperImpl implements ChurchMapper {

    @Override
    public List<ChurchResDto.getChurch> getChurchList(List<ChurchEntity> churchList) {
        if ( churchList == null ) {
            return null;
        }

        List<ChurchResDto.getChurch> list = new ArrayList<ChurchResDto.getChurch>( churchList.size() );
        for ( ChurchEntity churchEntity : churchList ) {
            list.add( churchEntityTogetChurch( churchEntity ) );
        }

        return list;
    }

    @Override
    public List<ChurchResDto.getBodyListByChurchId> getBodyListByChurchId(List<BodyEntity> bodyList) {
        if ( bodyList == null ) {
            return null;
        }

        List<ChurchResDto.getBodyListByChurchId> list = new ArrayList<ChurchResDto.getBodyListByChurchId>( bodyList.size() );
        for ( BodyEntity bodyEntity : bodyList ) {
            list.add( bodyEntityTogetBodyListByChurchId( bodyEntity ) );
        }

        return list;
    }

    protected ChurchResDto.getChurch churchEntityTogetChurch(ChurchEntity churchEntity) {
        if ( churchEntity == null ) {
            return null;
        }

        ChurchResDto.getChurch getChurch = new ChurchResDto.getChurch();

        return getChurch;
    }

    protected ChurchResDto.getBodyListByChurchId bodyEntityTogetBodyListByChurchId(BodyEntity bodyEntity) {
        if ( bodyEntity == null ) {
            return null;
        }

        ChurchResDto.getBodyListByChurchId getBodyListByChurchId = new ChurchResDto.getBodyListByChurchId();

        getBodyListByChurchId.setBodyId( bodyEntity.getBodyId() );
        getBodyListByChurchId.setBodyName( bodyEntity.getBodyName() );

        return getBodyListByChurchId;
    }
}
