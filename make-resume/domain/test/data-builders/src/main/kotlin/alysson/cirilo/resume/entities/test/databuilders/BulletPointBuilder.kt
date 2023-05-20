package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ProfessionalSkill

class BulletPointBuilder private constructor(
    private val content: List<BulletPointContent>,
) {
    constructor() : this(
        content = listOf(
            BulletPointContent.PlainText("Worked with "),
            BulletPointContent.Skill(ProfessionalSkill("kotlin")),
        ),
    )

    fun with(content: List<BulletPointContent>) = BulletPointBuilder(content)

    fun with(content: BulletPointContent) = with(listOf(content))

    fun thatReads(content: String) = with(BulletPointContent.PlainText(content))

    fun withSkill(skill: String) = with(BulletPointContent.Skill(ProfessionalSkill(skill)))

    fun withNoContent() = with(emptyList())

    fun appendText(plainText: String) =
        BulletPointBuilder(content + BulletPointContent.PlainText(plainText))

    fun appendSkill(skill: String) =
        BulletPointBuilder(content + BulletPointContent.Skill(ProfessionalSkill(skill)))

    fun build(): BulletPoint {
        return BulletPoint(content)
    }
}

fun aBulletPoint() = BulletPointBuilder()
fun anEmptyBulletPoint() = BulletPointBuilder().withNoContent()
