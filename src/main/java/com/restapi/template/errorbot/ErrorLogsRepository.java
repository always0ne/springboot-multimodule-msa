package com.restapi.template.errorbot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 에러로그 레포지터리.
 *
 * @author always0ne
 * @version 1.0
 */
@Repository
public interface ErrorLogsRepository extends JpaRepository<ErrorLogs, Long> {

}
