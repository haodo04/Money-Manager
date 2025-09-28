package haodo.dev.vn.moneymanager.repository;

import haodo.dev.vn.moneymanager.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

//    select * from tbl_categories where profile_id = ?
    List<CategoryEntity> findByProfileId(Long profileId);
}
