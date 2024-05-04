package com.sam.jobmicroservice.job.mapper;

import com.sam.jobmicroservice.job.Job;
import com.sam.jobmicroservice.job.dto.JobWithCompanyAndReview;
import com.sam.jobmicroservice.job.external.Company;
import com.sam.jobmicroservice.job.external.Review;

import java.util.List;

public class JobWithCompanyMapper {

    public static JobWithCompanyAndReview mapJobWithCompanyAndReview(Job job, Company company, List<Review> review) {
        JobWithCompanyAndReview jobWithCompanyAndReview = new JobWithCompanyAndReview();
        jobWithCompanyAndReview.setId(job.getId());
        jobWithCompanyAndReview.setDescription(job.getDescription());
        jobWithCompanyAndReview.setLocation(job.getLocation());
        jobWithCompanyAndReview.setTitle(job.getTitle());
        jobWithCompanyAndReview.setMaxSalary(job.getMaxSalary());
        jobWithCompanyAndReview.setMinSalary(job.getMinSalary());
        jobWithCompanyAndReview.setCompany(company);
        jobWithCompanyAndReview.setReview(review);
        return jobWithCompanyAndReview;
    }
}
