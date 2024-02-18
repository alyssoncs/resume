package alysson.cirilo.resume.drivers.utils.resource

fun String.asResource(): String {
    return Unit.javaClass.getResource(this)!!.readText()
}
