package com.bramix.metrics.reader.config;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GitHubClientConfig {
    @Bean
    public GitHub createGitHubClient() throws IOException {
        return new GitHubBuilder()
                .withOAuthToken("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")
                .build();
    }
}
