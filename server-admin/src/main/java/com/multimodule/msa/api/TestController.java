package com.multimodule.msa.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/t")
    public void NPE(){
        throw new NullPointerException();
    }
}
