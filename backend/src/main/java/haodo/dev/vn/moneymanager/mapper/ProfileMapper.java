package haodo.dev.vn.moneymanager.mapper;

import haodo.dev.vn.moneymanager.dto.ProfileDTO;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    // Map DTO -> Entity
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    ProfileEntity toEntity(ProfileDTO dto, @Context PasswordEncoder passwordEncoder);

    // Map Entity -> DTO
    ProfileDTO toDTO(ProfileEntity entity);
}
