package haodo.dev.vn.moneymanager.mapper;

import haodo.dev.vn.moneymanager.dto.IncomeDTO;
import haodo.dev.vn.moneymanager.entity.CategoryEntity;
import haodo.dev.vn.moneymanager.entity.IncomeEntity;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "profile.id", target = "profileId")
    IncomeDTO toDTO(IncomeEntity entity);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "profileId", target = "profile")
    IncomeEntity toEntity(IncomeDTO dto);

    default CategoryEntity mapCategory(Long id) {
        if (id == null) return null;
        CategoryEntity category = new CategoryEntity();
        category.setId(id);
        return category;
    }

    default ProfileEntity mapProfile(Long id) {
        if (id == null) return null;
        ProfileEntity profile = new ProfileEntity();
        profile.setId(id);
        return profile;
    }
}

