package haodo.dev.vn.moneymanager.controller;

import haodo.dev.vn.moneymanager.dto.ExpenseDTO;
import haodo.dev.vn.moneymanager.service.ExpenseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseController {
    ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO dto) {
        ExpenseDTO saved = expenseService.addExpense(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpense() {
        List<ExpenseDTO> expenses = expenseService.getCurrentMonthExpenseForCurrentUser();
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
