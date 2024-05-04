package com.sam.jobmicroservice.job.impl;
import com.sam.jobmicroservice.job.Job;
import com.sam.jobmicroservice.job.JobRepository;
import com.sam.jobmicroservice.job.JobService;
import com.sam.jobmicroservice.job.dto.JobWithCompanyAndReview;
import com.sam.jobmicroservice.job.external.Company;
import com.sam.jobmicroservice.job.external.Review;
import com.sam.jobmicroservice.job.mapper.JobWithCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    public JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyAndReview> findAllJobs() {
        List<Job> jobList = jobRepository.findAll();
        List<JobWithCompanyAndReview> jobWithCompanyAndReviewList = jobList.stream().map(this::getJobWithCompany).collect(Collectors.toList());
        return jobWithCompanyAndReviewList;
    }

    private JobWithCompanyAndReview getJobWithCompany(Job job) {
        Company company = restTemplate.getForObject("http://COMPANYMICROSERVICE:8081/companies/" + job.getCompanyId(), Company.class);
       ResponseEntity<List<Review>> reviewEntity = restTemplate.exchange("http://REVIEWMICROSERVICE/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
        });
       List<Review> reviewList = reviewEntity.getBody();
        JobWithCompanyAndReview jobWithCompanyAndReview = JobWithCompanyMapper.mapJobWithCompanyAndReview(job, company, reviewList);
        if(jobWithCompanyAndReview != null) {
            return jobWithCompanyAndReview;
        }
        return null;
    }

    @Override
    public Job postNewJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public JobWithCompanyAndReview findJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return getJobWithCompany(job);
    }

    @Override
    public Boolean deleteJobById(Long id) {
       try {
           jobRepository.deleteById(id);
           return true;
       }
       catch (Exception e) {
           e.printStackTrace();
           return false;
       }
    }

    @Override
    public Job updateJob(Long id, Job updatedJob) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if(optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            job.setCompanyId(updatedJob.getCompanyId());
            jobRepository.save(job);
            return job;
        }
        return null;
    }
}
