package haodo.dev.vn.moneymanager.service;

import haodo.dev.vn.moneymanager.dto.CategoryDTO;
import haodo.dev.vn.moneymanager.entity.CategoryEntity;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import haodo.dev.vn.moneymanager.mapper.CategoryMapper;
import haodo.dev.vn.moneymanager.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    ProfileService profileService;
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
       ProfileEntity profile = profileService.getCurrentProfile();
       if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())) {
           throw new RuntimeException("Category with this name already exist");
       }

       CategoryEntity newCategory = categoryMapper.toEntity(categoryDTO, profile);
       categoryRepository.save(newCategory);
       return categoryMapper.toDTO(newCategory);
    }

    public List<CategoryDTO> getCategoriesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByProfileId(profile.getId());
        return categories.stream().map(categoryMapper::toDTO).toList();
    }

    public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type) {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> entities = categoryRepository.findByTypeAndProfileId(type, profile.getId());
        return entities.stream().map(categoryMapper::toDTO).toList();
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(categoryId, profile.getId()).orElseThrow(
                () -> new RuntimeException("Category not found or not accessible")
        );
        existingCategory.setName(dto.getName());
        existingCategory.setIcon(dto.getIcon());
        existingCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(existingCategory);
    }
}
