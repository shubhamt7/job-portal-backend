package com.jobsportal.jobs_api.controller;
import com.jobsportal.jobs_api.entity.Job;
import com.jobsportal.jobs_api.repository.JobRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*") // allow frontend to access locally
public class JobController {

    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Fetch all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Fetch jobs by city
    @GetMapping("/city/{city}")
    public List<Job> getJobsByCity(@PathVariable String city) {
        return jobRepository.findByCity(city);
    }

    // Fetch jobs by country
    @GetMapping("/country/{country}")
    public List<Job> getJobsByCountry(@PathVariable String country) {
        return jobRepository.findByCountry(country);
    }

    // Fetch jobs by category
    @GetMapping("/category/{category}")
    public List<Job> getJobsByCategory(@PathVariable String category) {
        return jobRepository.findByCategory(category);
    }

    // Admin-only: create new job
    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

}
