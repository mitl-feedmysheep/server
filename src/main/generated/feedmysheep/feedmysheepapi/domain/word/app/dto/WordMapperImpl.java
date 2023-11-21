package feedmysheep.feedmysheepapi.domain.word.app.dto;

import feedmysheep.feedmysheepapi.models.WordEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T20:11:46+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (JetBrains s.r.o.)"
)
@Component
public class WordMapperImpl implements WordMapper {

    @Override
    public WordResDto.getWordByMainAndSubScreen getWordByMainAndSubScreen(WordEntity word) {
        if ( word == null ) {
            return null;
        }

        WordResDto.getWordByMainAndSubScreen getWordByMainAndSubScreen = new WordResDto.getWordByMainAndSubScreen();

        getWordByMainAndSubScreen.setWords( word.getWords() );
        getWordByMainAndSubScreen.setBook( word.getBook() );
        getWordByMainAndSubScreen.setChapter( word.getChapter() );
        getWordByMainAndSubScreen.setVerse( word.getVerse() );

        return getWordByMainAndSubScreen;
    }
}
