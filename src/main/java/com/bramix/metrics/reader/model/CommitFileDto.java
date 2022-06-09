package com.bramix.metrics.reader.model;

import lombok.Value;

@Value
public class CommitFileDto {
    String status;
    Integer changesCount;
    Integer additionsCount;
    Integer deletionsCount;
    String url;
    String patch;
}
