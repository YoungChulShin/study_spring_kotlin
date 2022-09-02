package study.querydsl.querydsl_test.domain

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import study.querydsl.querydsl_test.domain.QSchool.*
import javax.persistence.EntityManager

class SchoolRepositoryImpl(
    entityManager: EntityManager
) : SchoolRepositoryCustom, QuerydslRepositorySupport(School::class.java) {

    private val queryFactory = JPAQueryFactory(entityManager)

    override fun findSchoolWithName(name: String): List<School> {
        return queryFactory
            .select(school)
            .from(school)
            .where(nameEq(name))
            .fetch()
    }

    override fun findSchoolWithLocationAndAge(location: String, age: Int): List<School> {
        return queryFactory
            .select(school)
            .from(school)
            .where(locationEq(location), ageLoe(age))
            .fetch()
    }

    override fun findAllSchoolsByQuerydsl(): List<SchoolInfo> {
        return queryFactory
            .select(Projections.constructor(
                SchoolInfo::class.java,
                school.id,
                school.name,
                school.location,
                school.age))
            .from(school)
            .fetch()
    }

    override fun findAllSchoolOrderByAgeByQuerydsl(): List<School> {
        return queryFactory
            .select(school)
            .from(school)
            .orderBy(school.age.asc())
            .fetch()
    }

    override fun findAllSchoolWithPaging(pageRequest: PageRequest): Page<School> {
        val result = queryFactory
            .select(school)
            .from(school)
            .orderBy(school.age.asc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .fetch()

        val resultCount = queryFactory
            .select(school.id)
            .from(school)
            .fetch()
            .size
            .toLong()


        return PageImpl(result, pageRequest, resultCount)

    }

    override fun findAllSchoolWithPaging2(pageRequest: PageRequest): List<School> {
        val query = queryFactory
            .select(school)
            .from(school)
            .orderBy(school.age.asc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())

        val queryWithPaging = querydsl?.applyPagination(pageRequest, query)

        return queryWithPaging?.fetch() ?: listOf()

    }

    private fun nameEq(name: String): BooleanExpression {
        return school.name.eq(name)
    }

    private fun locationEq(location: String): BooleanExpression {
        return school.location.eq(location)
    }

    private fun ageLoe(age: Int): BooleanExpression {
        return school.age.loe(age)
    }
}