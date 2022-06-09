package com.bramix.metrics.reader.model;

import lombok.Value;

@Value
public class CommitInfoDto {
    String message;
    Integer commentCount;
    String committerName;
    String committerEmail;
}
