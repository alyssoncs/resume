package alysson.cirilo.resume.entities.testbuilders

fun <T> T.builderMethod(sideEffect: T.() -> Unit): T {
    return this.also(sideEffect)
}
