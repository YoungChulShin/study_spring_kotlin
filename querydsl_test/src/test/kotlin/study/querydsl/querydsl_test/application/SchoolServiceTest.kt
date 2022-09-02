package study.querydsl.querydsl_test.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import org.springframework.data.domain.PageRequest
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

    @BeforeEach
    fun init() {
        schoolService.addSchool("서울B초등학교", "서울", 15)
        schoolService.addSchool("서울C초등학교", "서울", 20)
        schoolService.addSchool("부산A초등학교", "부산", 1)
        schoolService.addSchool("부산B초등학교", "부산", 30)
        schoolService.addSchool("인천A초등학교", "인천", 8)
    }

    @Test
    fun `학교 생성`() {
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

    @Test
    fun `학교 이름 조회`() {
        val schools = schoolRepository.findSchoolWithName("서울C초등학교")
        Assertions.assertThat(schools).isNotNull
        Assertions.assertThat(schools.size).isEqualTo(1)
    }

    @Test
    fun `학교 지역과 10년 이하의 학교 조회`() {
        val schools = schoolRepository.findSchoolWithLocationAndAge("부산", 10)
        Assertions.assertThat(schools).isNotNull
        Assertions.assertThat(schools.size).isEqualTo(1)
        Assertions.assertThat(schools[0].name).isEqualTo("부산A초등학교")
    }

    @Test
    fun `쿼리메서드로 SchoolInfo 조회`() {
        val schools = schoolRepository.findAllSchools()
        Assertions.assertThat(schools).isNotNull
        Assertions.assertThat(schools.size).isEqualTo(5)
        println(schools)
    }

    @Test
    fun `querydsl로 SchoolInfo 조회`() {
        val schools = schoolRepository.findAllSchoolsByQuerydsl()
        Assertions.assertThat(schools).isNotNull
        Assertions.assertThat(schools.size).isEqualTo(5)
        println(schools)
    }

    @Test
    fun `age 정렬`() {
        val schools = schoolRepository.findAllByOrderByAgeAsc()
        Assertions.assertThat(schools).isNotNull
        Assertions.assertThat(schools[0].name).isEqualTo("부산A초등학교")
    }

    @Test
    fun `age 정렬 querydsl`() {
        val schools = schoolRepository.findAllSchoolOrderByAgeByQuerydsl()
        Assertions.assertThat(schools).isNotNull
        Assertions.assertThat(schools[0].name).isEqualTo("부산A초등학교")
    }

    @Test
    fun `age 정렬 페이징 querydsl`() {
        val schools = schoolRepository.findAllSchoolWithPaging(PageRequest.of(0, 2))

        Assertions.assertThat(schools.size).isEqualTo(2)
        Assertions.assertThat(schools.content[0].name).isEqualTo("부산A초등학교")
    }

    @Test
    fun `age 정렬 페이징 querydsl2`() {
        val schools = schoolRepository.findAllSchoolWithPaging2(PageRequest.of(0, 2))

        Assertions.assertThat(schools.size).isEqualTo(2)
        Assertions.assertThat(schools[0].name).isEqualTo("부산A초등학교")
    }

}
