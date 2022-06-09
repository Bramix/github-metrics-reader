package com.bramix.metrics.reader.mapper;

import com.bramix.metrics.reader.model.*;
import lombok.SneakyThrows;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHUser;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GithubCommitMapper {

    private static final String UNDEFINED = "undefined";

    @SneakyThrows
    public GithubCommitDto map(GHCommit ghCommit) {

        AuthorDto author = getAuthor(ghCommit.getAuthor());

        var commitShortInfo = ghCommit.getCommitShortInfo();

        var committer = new CommitInfoDto(commitShortInfo.getMessage(),
                commitShortInfo.getCommentCount(),
                commitShortInfo.getCommitter().getName(),
                commitShortInfo.getCommitter().getEmail());

        var owner = new OwnerDto(ghCommit.getOwner().getName(),
                ghCommit.getOwner().getFullName(),
                ghCommit.getOwner().getLanguage(),
                ghCommit.getOwner().getVisibility().name()
        );

        var commitFiles = ghCommit.getFiles()
                .stream()
                .map(file -> new CommitFileDto(file.getStatus(),
                        file.getLinesChanged(),
                        file.getLinesAdded(),
                        file.getLinesDeleted(),
                        file.getRawUrl().toString(),
                        file.getPatch())
                ).collect(Collectors.toList());

        return new GithubCommitDto(author, committer, owner, commitShortInfo.getCommitDate(), commitFiles);
    }

    private AuthorDto getAuthor(GHUser ghUser) {
        if (ghUser == null) {
            return new AuthorDto(UNDEFINED, UNDEFINED);
        }
        return new AuthorDto(ghUser.getLogin(),
                    ghUser.getUrl().toString());
    }
}
