package com.jobsportal.jobs_api.repository;

import com.jobsportal.jobs_api.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Example: Find jobs by city
    List<Job> findByCity(String city);

    // Example: Find jobs by country
    List<Job> findByCountry(String country);

    // Example: Find jobs by category
    List<Job> findByCategory(String category);
}
