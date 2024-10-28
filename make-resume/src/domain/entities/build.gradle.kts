plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

features {
    unitTests = true
}

//dependencies {
//    testImplementation(libs.test.junit.api)
//    testImplementation(libs.test.kotest.assertions)
//    testRuntimeOnly(libs.test.junit.engine)
//    testRuntimeOnly(libs.test.junit.launcher)
//}