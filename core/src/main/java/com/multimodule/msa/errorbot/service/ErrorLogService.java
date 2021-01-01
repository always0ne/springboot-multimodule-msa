package com.multimodule.msa.errorbot.service;

import com.multimodule.msa.errorbot.model.ErrorLogs;
import com.multimodule.msa.errorbot.repository.ErrorLogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ErrorLogService {

    private final ErrorLogsRepository errorLogsRepository;

    public Page<ErrorLogs> getErrorLogs(Pageable pageable) {
        return errorLogsRepository.findAll(pageable);
    }
}
