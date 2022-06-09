package com.bramix.metrics.reader.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class GithubReport implements Serializable {
    List<GithubCommitDto> ghCommits;
}
