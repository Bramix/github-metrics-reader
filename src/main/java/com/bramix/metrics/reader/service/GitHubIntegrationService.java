package com.bramix.metrics.reader.service;

import com.bramix.metrics.reader.redis.producer.GithubRedisProducer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class GitHubIntegrationService {
    private final GitHub gitHubClient;
    private final GithubRedisProducer githubRedisProducer;

    @SneakyThrows
    public List<GHCommit> retrieveCommitsByDate(Date startDate, Date endDate) {
        return gitHubClient.getMyself()
                .getAllRepositories().values()
                .parallelStream()
                .map(repo -> repo.queryCommits()
                        .since(startDate)
                        .until(endDate)
                        .list()
                )
                .flatMap(commmitsList -> {
                    try {
                        return commmitsList.toList().stream();
                    } catch (IOException e) {
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public List<GHCommit> retrieveCommitsByDateAndSendToKibana(Date startDate, Date endDate) {
        List<GHCommit> ghCommits = retrieveCommitsByDate(startDate, endDate);
        githubRedisProducer.send(ghCommits);
        return ghCommits;
    }
}
