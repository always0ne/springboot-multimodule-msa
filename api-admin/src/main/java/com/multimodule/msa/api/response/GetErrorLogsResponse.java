package com.multimodule.msa.api.response;

import com.multimodule.msa.errorbot.model.ErrorLogs;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class GetErrorLogsResponse extends EntityModel<ErrorLogs> {
    public GetErrorLogsResponse(ErrorLogs errorLogs, Link... links){
        super(errorLogs, links);
    }
}
