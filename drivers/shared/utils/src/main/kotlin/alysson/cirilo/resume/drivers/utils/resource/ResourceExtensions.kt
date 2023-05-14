package alysson.cirilo.resume.drivers.utils.resource

fun String.asResource(): String {
    return object {}.javaClass.getResource(this)!!.readText()
}
