package com.multimodule.msa.admin;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.multimodule.msa.authentication.UserStatus;
import com.multimodule.msa.common.BaseControllerTest;
import com.multimodule.msa.model.Users;
import com.multimodule.msa.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class getLockedUsersTest extends BaseControllerTest {

    @Autowired
    UsersRepository usersRepository;

    @Test
    @DisplayName("정지된 계정 조회")
    void getLockedUsersSuccess() throws Exception {
        // Given
        Users users = new Users("test", "test", "test", UserStatus.LOCKED, Collections.emptyList(), "test");
        usersRepository.save(users);
        // When
        ResultActions resultActions = this.mockMvc.perform(get("/admin/users/locked"));
        // Then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(MockMvcRestDocumentationWrapper.document("getLockedUsers",
                        resource(ResourceSnippetParameters.builder()
                                .description("정지된 계정 리스트 조회")
                                .responseFields(
                                        fieldWithPath("lockedUsers").type(JsonFieldType.ARRAY).description("정지된 리스트"),
                                        fieldWithPath("lockedUsers[].userId").type(JsonFieldType.STRING).description("유저 ID"),
                                        fieldWithPath("lockedUsers[].name").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("lockedUsers[].state").type(JsonFieldType.STRING).description("유저 상태")
                                )
                                .build())));
    }
}
