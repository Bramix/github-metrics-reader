package com.bramix.metrics.reader.redis.producer;

import com.bramix.metrics.reader.mapper.GithubCommitMapper;
import com.bramix.metrics.reader.model.GithubReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubRedisProducer {

    @Value("${github.stream.name}")
    private String streamKey;

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GithubCommitMapper githubCommitMapper;

    @SneakyThrows
    public void send(List<GHCommit> ghCommits){

        var commits = ghCommits.parallelStream()
                .map(githubCommitMapper::map)
                .collect(Collectors.toList());

        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .in(streamKey)
                .ofObject(objectMapper.writeValueAsString(new GithubReport(commits)))
                .withId(RecordId.autoGenerate());

        redisTemplate.opsForStream()
                .add(record);
    }

}