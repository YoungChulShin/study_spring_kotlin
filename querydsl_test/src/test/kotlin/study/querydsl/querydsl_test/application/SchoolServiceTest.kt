package study.querydsl.querydsl_test.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.querydsl.querydsl_test.domain.SchoolRepository

@SpringBootTest
@Transactional
internal class SchoolServiceTest(
    @Autowired
    val schoolService: SchoolService,

    @Autowired
    val schoolRepository: SchoolRepository,
) {

    @Test
    fun create_school() {
        val name = "서울A초등학교"
        val location = "서울"
        val age = 10

        val addSchool = schoolService.addSchool(
            name = name,
            location = location,
            age = age
        )

        val findSchool = schoolRepository.findById(addSchool.id!!).orElse(null)

        Assertions.assertThat(findSchool).isNotNull
        Assertions.assertThat(findSchool.name).isEqualTo(name)
        Assertions.assertThat(findSchool.location).isEqualTo(location)
        Assertions.assertThat(findSchool.age).isEqualTo(age)
    }


}
