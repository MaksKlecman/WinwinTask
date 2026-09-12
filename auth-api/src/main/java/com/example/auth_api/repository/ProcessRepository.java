package com.example.auth_api.repository;

import com.example.auth_api.model.ProcessingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessingLog, UUID> {

}
