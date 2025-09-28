package haodo.dev.vn.moneymanager.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {
    Long id;

    private Long profileId;

    String name;

    String type;

    String icon;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
