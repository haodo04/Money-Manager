package haodo.dev.vn.moneymanager.service;

import haodo.dev.vn.moneymanager.dto.ExpenseDTO;
import haodo.dev.vn.moneymanager.entity.CategoryEntity;
import haodo.dev.vn.moneymanager.entity.ExpenseEntity;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import haodo.dev.vn.moneymanager.mapper.ExpenseMapper;
import haodo.dev.vn.moneymanager.repository.CategoryRepository;
import haodo.dev.vn.moneymanager.repository.ExpenseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseService {
    CategoryRepository categoryRepository;
    ExpenseRepository expenseRepository;
    ExpenseMapper expenseMapper;
    ProfileService profileService;

    public ExpenseDTO addExpense(ExpenseDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ExpenseEntity newExpense = expenseMapper.toEntity(dto);
        newExpense.setProfile(profile);
        newExpense.setCategory(category);

        newExpense = expenseRepository.save(newExpense);
        return expenseMapper.toDTO(newExpense);
    }

    // Retrieves all expenses for current month/based on start date and end date
    public List<ExpenseDTO> getCurrentMonthExpenseForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
        return list.stream().map(expenseMapper::toDTO).toList();

    }

    // delete expense by id for current user
    public void deleteExpense(Long expenseId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        ExpenseEntity entity = expenseRepository.findById(expenseId).orElseThrow(() ->
                new RuntimeException("Expense not found"));
        if(!entity.getProfile().getId().equals(profile.getId()))
            throw new RuntimeException("Unauthorized to delete this expense");
        expenseRepository.delete(entity);
    }

    // Get latest 5 expenses for current user
    public List<ExpenseDTO> getLatest5ExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(expenseMapper::toDTO).toList();
    }

    // Get total expenses for current user
    public BigDecimal getTotalExpenseForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal total = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return total != null ? total : BigDecimal.ZERO;
    }

    // filter expenses
    public List<ExpenseDTO> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase
                (profile.getId(), startDate, endDate, keyword, sort);
        return list.stream().map(expenseMapper::toDTO).toList();
    }

    // notifications
    public List<ExpenseDTO> getExpensesForUserOnDate(Long profileId, LocalDate date) {
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDate(profileId, date);
        return list.stream().map(expenseMapper::toDTO).toList();
    }
}
