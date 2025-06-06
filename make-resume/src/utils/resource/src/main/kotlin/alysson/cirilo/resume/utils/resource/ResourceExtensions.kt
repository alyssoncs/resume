package alysson.cirilo.resume.utils.resource

import java.net.URL

fun String.read(): String {
    return asResource().readText()
}

fun String.asResource(): URL {
    return Unit.javaClass.getResource(this)!!
}
