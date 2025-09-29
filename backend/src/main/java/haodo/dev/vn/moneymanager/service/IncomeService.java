package haodo.dev.vn.moneymanager.service;

import haodo.dev.vn.moneymanager.dto.ExpenseDTO;
import haodo.dev.vn.moneymanager.dto.IncomeDTO;
import haodo.dev.vn.moneymanager.entity.CategoryEntity;
import haodo.dev.vn.moneymanager.entity.ExpenseEntity;
import haodo.dev.vn.moneymanager.entity.IncomeEntity;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import haodo.dev.vn.moneymanager.mapper.IncomeMapper;
import haodo.dev.vn.moneymanager.repository.CategoryRepository;
import haodo.dev.vn.moneymanager.repository.IncomeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IncomeService {
    CategoryRepository categoryRepository;
    IncomeRepository incomeRepository;
    IncomeMapper incomeMapper;
    ProfileService profileService;


    public IncomeDTO addIncome(IncomeDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        IncomeEntity newIncome = incomeMapper.toEntity(dto);
        newIncome.setProfile(profile);
        newIncome.setCategory(category);

        newIncome = incomeRepository.save(newIncome);
        return incomeMapper.toDTO(newIncome);
    }

    // Retrieves all incomes for current month/based on start date and end date
    public List<IncomeDTO> getCurrentMonthIncomeForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
        return list.stream().map(incomeMapper::toDTO).toList();

    }

    // delete income by id for current user
    public void deleteIncome(Long incomeId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        IncomeEntity entity = incomeRepository.findById(incomeId).orElseThrow(() ->
                new RuntimeException("Income not found"));
        if(!entity.getProfile().getId().equals(profile.getId()))
            throw new RuntimeException("Unauthorized to delete this income");
        incomeRepository.delete(entity);
    }

    // Get latest 5 incomes for current user
    public List<IncomeDTO> getLatest5IncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(incomeMapper::toDTO).toList();
    }

    // Get total incomes for current user
    public BigDecimal getTotalIncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal total = incomeRepository.findTotalIncomeByProfileId(profile.getId());
        return total != null ? total : BigDecimal.ZERO;
    }
}
