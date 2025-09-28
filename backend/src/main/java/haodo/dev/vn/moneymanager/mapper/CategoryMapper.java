package haodo.dev.vn.moneymanager.mapper;

import haodo.dev.vn.moneymanager.dto.CategoryDTO;
import haodo.dev.vn.moneymanager.entity.CategoryEntity;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "profile", source = "profile")
    CategoryEntity toEntity(CategoryDTO dto, ProfileEntity profile);

    @Mapping(target = "profileId", source = "profile.id")
    CategoryDTO toDTO(CategoryEntity entity);

}
