package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.Role

class RoleBuilder {
    private var title: String = "Software Engineer"
    private var bulletPointBuilders: List<BulletPointBuilder> = listOf(BulletPointBuilder())
    private var enrollmentPeriodBuilder: EnrollmentPeriodBuilder = period()

    fun with(bulletPointBuilders: List<BulletPointBuilder>) = builderMethod {
        this.bulletPointBuilders = bulletPointBuilders
    }

    fun with(bulletPointBuilder: BulletPointBuilder) = with(listOf(bulletPointBuilder))

    fun withNoBulletPoints() = with(emptyList())

    fun append(bulletPointBuilder: BulletPointBuilder) = builderMethod {
        this.bulletPointBuilders += bulletPointBuilder
    }

    fun `as`(title: String) = builderMethod {
        this.title = title
    }

    fun between(enrollmentPeriodBuilder: EnrollmentPeriodBuilder) = builderMethod {
        this.enrollmentPeriodBuilder = enrollmentPeriodBuilder
    }

    fun build(): Role {
        return Role(
            title = title,
            period = enrollmentPeriodBuilder.build(),
            bulletPoints = bulletPointBuilders.map(BulletPointBuilder::build),
        )
    }
}

fun aRole() = RoleBuilder()
