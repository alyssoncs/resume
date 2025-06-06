package alysson.cirilo.resume.utils.resource

fun String.asResource(): String {
    return Unit.javaClass.getResource(this)!!.readText()
}
