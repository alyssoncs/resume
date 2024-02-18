package alysson.cirilo.resume.entities

class RoleBuilder private constructor(
    private val title: String,
    private val bulletPointBuilders: List<BulletPointBuilder>,
    private val enrollmentPeriodBuilder: EnrollmentPeriodBuilder,
) {
    constructor() : this(
        title = "Software Engineer",
        bulletPointBuilders = listOf(BulletPointBuilder()),
        enrollmentPeriodBuilder = period(),
    )

    fun with(bulletPointBuilders: List<BulletPointBuilder>) =
        RoleBuilder(title, bulletPointBuilders, enrollmentPeriodBuilder)

    fun with(bulletPointBuilder: BulletPointBuilder) = with(listOf(bulletPointBuilder))

    fun withNoBulletPoints() = with(emptyList())

    fun append(bulletPointBuilder: BulletPointBuilder) =
        with(bulletPointBuilders + bulletPointBuilder)

    fun `as`(title: String) =
        RoleBuilder(title, bulletPointBuilders, enrollmentPeriodBuilder)

    fun between(enrollmentPeriodBuilder: EnrollmentPeriodBuilder) =
        RoleBuilder(title, bulletPointBuilders, enrollmentPeriodBuilder)

    fun build(): Role {
        return Role(
            title = title,
            period = enrollmentPeriodBuilder.build(),
            bulletPoints = bulletPointBuilders.map(BulletPointBuilder::build),
        )
    }
}

fun aRole() = RoleBuilder()
