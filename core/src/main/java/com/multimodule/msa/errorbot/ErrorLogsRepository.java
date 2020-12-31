package com.multimodule.msa.errorbot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogsRepository extends JpaRepository<ErrorLogs, Long> {

}
