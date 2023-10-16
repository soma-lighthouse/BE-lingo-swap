package com.lighthouse.lingoswap.batch.config;

import com.lighthouse.lingoswap.batch.processing.MatchedMemberItemProcessor;
import com.lighthouse.lingoswap.batch.processing.MatchedMemberItemWriter;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.member.application.MemberService;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class MatchedMemberBatchConfig {

    public final MemberService memberService;
    public final EntityManagerFactory entityManagerFactory;
    public final MatchedMemberItemProcessor matchedMemberItemProcessor;

    @Bean
    public Job createMatchedMembersJob(JobRepository jobRepository, Step createMatchedMembersStep) {
        return new JobBuilder("createMatchedMembersJob", jobRepository)
                .flow(createMatchedMembersStep)
                .end()
                .build();
    }

    @Bean
    public Step createMatchedMembersStep(JobRepository jobRepository,
                                         PlatformTransactionManager transactionManager,
                                         ItemReader<Member> reader,
                                         ItemProcessor<Member, List<MatchedMember>> processor,
                                         ItemWriter<List<MatchedMember>> writer) {
        return new StepBuilder("createMatchedMembersStep", jobRepository)
                .<Member, List<MatchedMember>>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Member> reader(EntityManagerFactory entityManagerFactory) {

        return new JpaPagingItemReaderBuilder<Member>()
                .name("reader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT m FROM Member m")
                .pageSize(1000)
                .build();
    }

    @Bean
    public ItemProcessor<Member, List<MatchedMember>> processor() {
        return new MatchedMemberItemProcessor(memberService);
    }

    @Bean
    public ItemWriter<List<MatchedMember>> writer() {
        return new MatchedMemberItemWriter(entityManagerFactory);
    }

}
