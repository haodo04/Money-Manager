package haodo.dev.vn.moneymanager.controller;

import haodo.dev.vn.moneymanager.service.DashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {
    DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardDate() {
        Map<String, Object> dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}
