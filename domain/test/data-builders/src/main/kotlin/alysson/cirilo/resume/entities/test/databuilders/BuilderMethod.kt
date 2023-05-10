package alysson.cirilo.resume.entities.test.databuilders

internal fun <T> T.builderMethod(sideEffect: T.() -> Unit): T {
    return this.also(sideEffect)
}
