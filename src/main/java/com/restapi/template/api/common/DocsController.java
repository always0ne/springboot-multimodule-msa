package com.restapi.template.api.common;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Docs 링크 제공을 위한 컨트롤러.
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/docs/index.html")
public class DocsController {
}
