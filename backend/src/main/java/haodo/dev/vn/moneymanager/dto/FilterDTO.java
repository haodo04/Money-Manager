package haodo.dev.vn.moneymanager.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterDTO {

    String type;

    LocalDate startDate;

    LocalDate endDate;

    String keyword;

    String sortField;  // date, amount, name

    String sortOrder;  // asc or desc
}
