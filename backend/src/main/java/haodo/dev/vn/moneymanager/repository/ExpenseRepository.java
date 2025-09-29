package haodo.dev.vn.moneymanager.repository;

import haodo.dev.vn.moneymanager.entity.ExpenseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    // select * from tbl_expenses where profile_id = ? order by desc
    List<ExpenseEntity> findByProfileIdOrderByDateDesc(long profileId);

    // select * from tbl_expenses where profile_id = ? order by
    // date desc limit 5
    List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

    @Query("select sum(e.amount) from ExpenseEntity e where e.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

    // select * from tbl_expenses where profile_id = ? and date between ? and ?
    // and name like %?% order by date desc
    List<ExpenseEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

    // select * from tbl_expenses where profile_id = ? and date between ? and ?
    List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

    // select * from tbl_expenses where profile_id = ? and date = ?
    List<ExpenseEntity> findByProfileIdAndDate(Long profileId, LocalDate date);

}
