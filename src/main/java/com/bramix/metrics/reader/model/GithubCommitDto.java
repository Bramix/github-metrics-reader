package com.bramix.metrics.reader.model;

import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class GithubCommitDto {
    AuthorDto author;
    CommitInfoDto committer;
    OwnerDto owner;
    Date commitDate;
    List<CommitFileDto> commitFiles;
}
