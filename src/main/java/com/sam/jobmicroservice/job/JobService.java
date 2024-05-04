package com.sam.jobmicroservice.job;

import com.sam.jobmicroservice.job.dto.JobWithCompanyAndReview;

import java.util.List;

public interface JobService {

    List<JobWithCompanyAndReview> findAllJobs();
    Job postNewJob(Job job);
    JobWithCompanyAndReview findJobById(Long id);
    Boolean deleteJobById(Long id);

    Job updateJob(Long id, Job job);
}
