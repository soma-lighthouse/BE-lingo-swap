package com.lighthouse.lingoswap.batch.listener;

import com.lighthouse.lingoswap.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MatchedMemberListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(MatchedMemberListener.class);
    private final JdbcTemplate jdbcTemplate;
    private final MatchService matchService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(">>> Clearing 'matched_member' table");
        matchService.delete();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED!");
        }
    }

}
