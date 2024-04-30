package com.sam.jobmicroservice.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAllJobs() {
        List<Job> allJobs = jobService.findAllJobs();
        return new ResponseEntity<>(allJobs, HttpStatus.OK);
    }

    @PostMapping("/newJob")
    public ResponseEntity<Job> addNewJob(@RequestBody Job job) {
        Job addedJob = jobService.postNewJob(job);
        return new ResponseEntity<>(addedJob, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable long id) {
        Job foundJob = jobService.findJobById(id);
        if(foundJob != null) {
            return new ResponseEntity<>(foundJob, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable long id) {
        boolean deleted = jobService.deleteJobById(id);
        if(deleted) {
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable long id, @RequestBody Job job) {
        Job modifiedJob = jobService.updateJob(id, job);
        if(modifiedJob != null) {
            return new ResponseEntity<>(modifiedJob, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
