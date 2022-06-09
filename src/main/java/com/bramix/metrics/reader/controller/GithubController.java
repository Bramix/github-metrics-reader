package com.bramix.metrics.reader.controller;


import com.bramix.metrics.reader.service.GitHubIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@AllArgsConstructor
public class GithubController {
    private final GitHubIntegrationService gitHubIntegrationService;

    @Operation(description = "API для старту інтеграції з Github з можливістю обрати період для виборки данних")
    @PostMapping("/gitHub")
    public void retrieveCommits(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                                @RequestParam boolean shouldBeSaved) {
        if (shouldBeSaved) {
            gitHubIntegrationService.retrieveCommitsByDateAndSendToKibana(startDate, endDate);
        }
        gitHubIntegrationService.retrieveCommitsByDate(startDate, endDate);
    }

}
