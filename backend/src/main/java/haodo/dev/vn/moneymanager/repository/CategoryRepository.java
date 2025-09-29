package haodo.dev.vn.moneymanager.repository;

import haodo.dev.vn.moneymanager.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

//    select * from tbl_categories where profile_id = ?
    List<CategoryEntity> findByProfileId(Long profileId);

//    select * from tbl_categories where id = ? and profileId = ?
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

//    select * from tbl_categories where type = ? and profile_id = ?
    List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);

    Boolean existsByNameAndProfileId(String name, Long profileId);

}
