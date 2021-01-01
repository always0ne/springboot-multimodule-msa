package com.multimodule.msa.api.controller;

import com.multimodule.msa.api.response.GetErrorLogsResponse;
import com.multimodule.msa.errorbot.service.ErrorLogService;
import com.multimodule.msa.errorbot.model.ErrorLogs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class LogController {

    private final ErrorLogService errorLogService;

    @GetMapping("/error")
    public PagedModel<GetErrorLogsResponse> getErrorLogs(
            Pageable pageable,
            PagedResourcesAssembler<ErrorLogs> assembler
    ){
        return assembler.toModel(errorLogService.getErrorLogs(pageable), GetErrorLogsResponse::new);
    }
}
