package haodo.dev.vn.moneymanager.service;

import haodo.dev.vn.moneymanager.dto.ExpenseDTO;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import haodo.dev.vn.moneymanager.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationService {

    ProfileRepository profileRepository;
    EmailService emailService;
    ExpenseService expenseService;

    @Value("${money.manager.frontend.url}")
    static String frontendUrl;

    @Scheduled(cron = "0 0 22 * * *", zone = "Asia/Ho_Chi_Minh")
    public void sendDailyIncomeExpenseReminder() {
        log.info("Job started: sendDailyIncomeExpenseReminder()");
        List<ProfileEntity> profiles = profileRepository.findAll();

        for (ProfileEntity profile : profiles) {
            String body = "Hi " + profile.getFullname() + ",<br><br>"
                    + "This is a friendly reminder to add your income and expenses for today in Money Manager.<br><br>"
                    + "<a href='" + frontendUrl + "' style='display:inline-block;"
                    + "padding:10px 20px;background-color:#4CAF50;color:#fff;text-decoration:none;border-radius:5px;font-weight:bold;'>"
                    + "Go to Money Manager</a>"
                    + "<br><br>Best regards,<br>Money Manager Team";

            emailService.sendEmail(profile.getEmail(), "Daily Reminder: Add your income and expenses", body);
        }
        log.info("Job completed: sendDailyIncomeExpenseReminder()");
    }

    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Ho_Chi_Minh")
    public void sendDailyExpenseSummary() {
        log.info("Job started: sendDailyExpenseSummary()");
        List<ProfileEntity> profiles = profileRepository.findAll();
        for(ProfileEntity profile : profiles) {
            List<ExpenseDTO> todayExpenses = expenseService.getExpensesForUserOnDate(profile.getId(), LocalDate.now());
            if (!todayExpenses.isEmpty()) {
                StringBuilder table = new StringBuilder();
                table.append("<table style='border-collapse:collapse;width:100%;'>");
                table.append("<tr style='background-color:#f2f2f2;'>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>S.No</th>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>Name</th>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>Amount</th>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>Category</th>")
                        .append("</tr>");

                int i = 1;
                for (ExpenseDTO expense : todayExpenses) {
                    table.append("<tr>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>").append(i++).append("</td>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>").append(expense.getName()).append("</td>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>").append(expense.getAmount()).append("</td>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>")
                            .append(expense.getCategoryId() != null ? expense.getCategoryName() : "N/A")
                            .append("</td>")
                            .append("</tr>");
                }
                table.append("</table>");

                String body = "Hi " + profile.getFullname() + ",<br/><br/>"
                        + "Here is a summary of your expenses for today:<br/><br/>"
                        + table
                        + "<br/><br/>Best regards,<br/>Money Manager Team";
                emailService.sendEmail(profile.getEmail(), "Your daily Expenses summary", body);
            }
        }
        log.info("Job started: sendDailyExpenseSummary()");
    }
}
