package study.spring.restdocstest.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import study.spring.restdocstest.application.UserService
import study.spring.restdocstest.domain.Gender
import study.spring.restdocstest.domain.User

// The RestDocumentationExtension is automatically configured with an output directory based on your project’s build tool
// gradle: build/generated-snippets
@ExtendWith(value = [RestDocumentationExtension::class])
@WebMvcTest(value = [UserController::class])
internal class UserControllerTest {

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var userService: UserService

    @BeforeEach
    fun setup(
        webApplicationContext: WebApplicationContext,
        restDocumentation: RestDocumentationContextProvider
    ) {
        // documentationConfiguration
        // The configurer applies sensible defaults and also provides an API for customizing the configuration
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder?>(documentationConfiguration(restDocumentation)
                .operationPreprocessors().apply {
                    withRequestDefaults(prettyPrint())
                    withResponseDefaults(prettyPrint())
                }
//                .uris().apply {
//                    withPort(9999)
//                    withHost("my-service.com") }
                )
            .build()

    }

    @Test
    @DisplayName("회원 가입 테스트")
    fun user_save() {
        // given
        val user = User(
            name = "ycshin",
            age = 39,
            gender = Gender.MALE
        )

        val userResponse = User(
            1L,
            name = "ycshin",
            age = 39,
            gender = Gender.MALE
        )

        Mockito.`when`(userService.save(user))
            .thenReturn(userResponse)

        // when
        // Document the call to the service, writing the snippets into a directory named index (which is located beneath the configured output directory).
        // The snippets are written by a RestDocumentationResultHandler.
        // You can obtain an instance of this class from the static document method on org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(user))
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("id").value(1L))
            .andExpect(jsonPath("name").value("ycshin"))
            .andExpect(jsonPath("age").value(39))
            .andExpect(jsonPath("gender").value("MALE"))
            .andDo(MockMvcRestDocumentation.document("users/create",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("age").type(JsonFieldType.STRING).description("사용자 나이"),
                    fieldWithPath("gender").type(JsonFieldType.STRING).description("사용자 성별"),
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 아이디"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("age").type(JsonFieldType.STRING).description("사용자 나이"),
                    fieldWithPath("gender").type(JsonFieldType.STRING).description("사용자 성별"),
                )
            ))
    }
}