package haodo.dev.vn.moneymanager.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDTO {
    Long id;

    String fullname;

    String email;

    String password;

    String profileImage;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
