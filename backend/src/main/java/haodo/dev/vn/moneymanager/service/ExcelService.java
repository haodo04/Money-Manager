package haodo.dev.vn.moneymanager.service;


import haodo.dev.vn.moneymanager.entity.IncomeEntity;
import haodo.dev.vn.moneymanager.repository.IncomeRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExcelService {
    private final IncomeRepository incomeRepository;

    public byte[] exportIncomeToExcel() throws IOException {
        List<IncomeEntity> incomes = incomeRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Incomes");

            // Header
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Icon", "Date", "Amount", "Category", "Profile"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            // Format date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Data rows
            int rowIdx = 1;
            for (IncomeEntity income : incomes) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(income.getId());
                row.createCell(1).setCellValue(income.getName());
                row.createCell(2).setCellValue(income.getIcon());
                row.createCell(3).setCellValue(income.getDate() != null ? income.getDate().format(formatter) : "");
                row.createCell(4).setCellValue(income.getAmount() != null ? income.getAmount().doubleValue() : 0.0);
                row.createCell(5).setCellValue(income.getCategory() != null ? income.getCategory().getName() : "");
                row.createCell(6).setCellValue(income.getProfile() != null ? income.getProfile().getFullname() : "");
            }

            // Auto size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to byte array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }
}
