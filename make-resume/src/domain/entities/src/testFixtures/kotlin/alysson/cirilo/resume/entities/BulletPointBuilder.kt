package alysson.cirilo.resume.entities

class BulletPointBuilder private constructor(
    private val content: List<BulletPointContent>,
) {
    constructor() : this(
        content = listOf(
            BulletPointContent.PlainText("Worked with "),
            BulletPointContent.Skill("kotlin"),
        ),
    )

    fun with(content: List<BulletPointContent>) = BulletPointBuilder(content)

    fun with(content: BulletPointContent) = with(listOf(content))

    fun containing(vararg bullets: String): BulletPointBuilder {
        val content = bullets.mapIndexed { idx, bullet ->
            if (idx % 2 != 0)
                BulletPointContent.Skill(bullet)
            else
                BulletPointContent.PlainText(bullet)
        }
        return with(content)
    }

    fun thatReads(content: String) = with(BulletPointContent.PlainText(content))

    fun withSkill(skill: String) = with(BulletPointContent.Skill(skill))

    fun withNoContent() = with(emptyList())

    fun appendText(plainText: String) =
        BulletPointBuilder(content + BulletPointContent.PlainText(plainText))

    fun appendSkill(skill: String) =
        BulletPointBuilder(content + BulletPointContent.Skill(skill))

    fun build(): BulletPoint {
        return BulletPoint(content)
    }
}

fun aBulletPoint() = BulletPointBuilder()
fun anEmptyBulletPoint() = BulletPointBuilder().withNoContent()
