package haodo.dev.vn.moneymanager.controller;

import haodo.dev.vn.moneymanager.service.EmailService;
import haodo.dev.vn.moneymanager.service.ExcelService;
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
    ExcelService incomeExcelService;
    EmailService emailService;

    @GetMapping("excel/download/income")
    public ResponseEntity<byte[]> downloadIncomeExcel() throws IOException {
        byte[] excelFile = incomeExcelService.exportIncomeToExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=income_details.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelFile);
    }

    @GetMapping("email/income-excel")
    public ResponseEntity<String> sendIncomeExcelToEmail() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            byte[] excelBytes = incomeExcelService.exportIncomeToExcel();

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

}
