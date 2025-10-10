package haodo.dev.vn.moneymanager.controller;

import haodo.dev.vn.moneymanager.service.EmailService;
import haodo.dev.vn.moneymanager.service.ExcelService;
import haodo.dev.vn.moneymanager.service.ExpenseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExcelController {
    ExcelService excelService;
    EmailService emailService;

    @GetMapping("excel/download/income")
    public ResponseEntity<byte[]> downloadIncomeExcel() throws IOException {
        byte[] excelFile = excelService.exportIncomeToExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=income_details.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelFile);
    }

    @GetMapping("excel/download/expense")
    public ResponseEntity<byte[]> downloadExpenseExcel() throws IOException {
        byte[] excelFile = excelService.exportExpenseToExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expense_details.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelFile);
    }

    @GetMapping("email/income-excel")
    public ResponseEntity<String> sendIncomeExcelToEmail() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            byte[] excelBytes = excelService.exportIncomeToExcel();

            emailService.sendEmailWithAttachment(
                    email,
                    "Income Report",
                    "Hello,\n\nPlease find attached your income report in Excel format.\n\nBest regards,\nMoney Manager Team",
                    excelBytes,
                    "income-report.xlsx"
            );

            return ResponseEntity.ok("Email sent successfully with Excel attachment");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

    @GetMapping("email/expense-excel")
    public ResponseEntity<String> sendExpenseExcelToEmail() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            byte[] excelBytes = excelService.exportExpenseToExcel();

            emailService.sendEmailWithAttachment(
                    email,
                    "Income Report",
                    "Hello,\n\nPlease find attached your expense report in Excel format.\n\nBest regards,\nMoney Manager Team",
                    excelBytes,
                    "expense-report.xlsx"
            );

            return ResponseEntity.ok("Email sent successfully with Excel attachment");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

}
