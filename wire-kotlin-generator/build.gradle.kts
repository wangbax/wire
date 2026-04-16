
plugins {
  id("java-library")
  kotlin("jvm")
}

dependencies {
  api(projects.wireSchema)
  implementation(projects.wireGrpcApi)
  implementation(projects.wireRuntime)
  implementation(libs.okio.core)
  api(libs.kotlinpoet)
  testImplementation(projects.wireTestUtils)
  testImplementation(libs.kotlin.test.junit)
  testImplementation(libs.assertk)
}
