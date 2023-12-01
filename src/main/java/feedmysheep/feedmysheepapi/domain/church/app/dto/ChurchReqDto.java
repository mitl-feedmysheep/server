package feedmysheep.feedmysheepapi.domain.church.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Data
public class ChurchReqDto {

//  @AllArgsConstructor
//  @Getter
//  public static class mainAndSubScreen {
//    @NotEmpty(message = "메인 스크린 값은 존재해야해요.")
//    private String mainScreen;
//    @Nullable
//    private String subScreen;
//  }

    @Getter
    @RequiredArgsConstructor
    public static class Church {
        // 필수값
        private String churchName;
        private String churchLocation;

        //필수값 x
        private String churchLogoUrl;
        private String churchNumber;
        private String homepageUrl;
        private String churchDescription;
    }
}