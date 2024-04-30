package com.sam.jobmicroservice.job;

import java.util.List;

public interface JobService {

    List<Job> findAllJobs();
    Job postNewJob(Job job);
    Job findJobById(Long id);
    Boolean deleteJobById(Long id);

    Job updateJob(Long id, Job job);
}
