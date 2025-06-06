package alysson.cirilo.resume.utils.resource

fun String.read(): String {
    return Unit.javaClass.getResource(this)!!.readText()
}
