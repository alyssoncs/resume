package alysson.cirilo.resume.entities.testbuilders

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ProfessionalSkill

class BulletPointBuilder {
    private var content: List<BulletPointContent> = listOf(
        BulletPointContent.PlainText("Worked with "),
        BulletPointContent.Skill(ProfessionalSkill("kotlin")),
    )

    fun with(content: List<BulletPointContent>) = builderMethod {
        this.content = content
    }

    fun with(content: BulletPointContent) = with(listOf(content))

    fun thatReads(content: String) = with(BulletPointContent.PlainText(content))

    fun withSkill(skill: String) = with(BulletPointContent.Skill(ProfessionalSkill(skill)))

    fun withNoContent() = builderMethod {
        this.content = emptyList()
    }

    fun appendText(plainText: String) = builderMethod {
        this.content += BulletPointContent.PlainText(plainText)
    }

    fun appendSkill(skill: String) = builderMethod {
        this.content += BulletPointContent.Skill(ProfessionalSkill(skill))
    }

    fun build(): BulletPoint {
        return BulletPoint(content)
    }
}

fun aBulletPoint() = BulletPointBuilder()
fun anEmptyBulletPoint() = BulletPointBuilder().withNoContent()
