package com.restapi.template.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.template.testfactory.AccountFactory;
import com.restapi.template.testfactory.CommentFactory;
import com.restapi.template.testfactory.PostFactory;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "templet.restapi.com", uriPort = 443)
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Transactional
@Disabled
public class BaseControllerTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected PostFactory postFactory;

  @Autowired
  protected CommentFactory commentFactory;

  @Autowired
  protected AccountFactory accountFactory;

}
