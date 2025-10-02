package haodo.dev.vn.moneymanager.mapper;

import haodo.dev.vn.moneymanager.dto.ProfileDTO;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "activationToken", ignore = true)
    ProfileEntity toEntity(ProfileDTO dto, @Context PasswordEncoder passwordEncoder);

    ProfileDTO toDTO(ProfileEntity entity);
}

