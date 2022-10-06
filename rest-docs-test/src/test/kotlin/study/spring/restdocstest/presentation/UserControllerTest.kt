package study.spring.restdocstest.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import study.spring.restdocstest.application.UserService
import study.spring.restdocstest.domain.Gender
import study.spring.restdocstest.domain.User

@WebMvcTest(value = [UserController::class])
internal class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var userService: UserService

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
    }
}