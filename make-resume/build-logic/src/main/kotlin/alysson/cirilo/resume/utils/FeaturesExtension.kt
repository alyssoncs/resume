package alysson.cirilo.resume.utils

import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.property

open class FeaturesExtension(
    objects: ObjectFactory,
    private val enableTests: () -> Unit,
) {
    private val unitTestsProperty = objects.property<Boolean>().convention(false)

    var unitTests: Boolean
        get() = unitTestsProperty.get()
        set(value) {
            unitTestsProperty = value
            unitTestsProperty.disallowChanges()
            if (unitTestsProperty.get()) {
                enableTests()
            }
        }
}
