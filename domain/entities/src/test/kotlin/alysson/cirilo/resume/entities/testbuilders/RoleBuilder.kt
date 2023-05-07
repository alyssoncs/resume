package alysson.cirilo.resume.entities.testbuilders

import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.Role
import java.time.LocalDate

class RoleBuilder {
    private var title: String = "Software Engineer"
    private var bulletPointBuilders: List<BulletPointBuilder> = listOf(BulletPointBuilder())

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

    fun build(): Role {
        return Role(
            title = title,
            period = EnrollmentPeriod(
                start = LocalDate.now(),
                end = EnrollmentPeriod.EndDate.Present,
            ),
            bulletPoints = bulletPointBuilders.map(BulletPointBuilder::build),
        )
    }
}

fun aRole() = RoleBuilder()
