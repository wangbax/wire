import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.attributes.Bundling
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.attributes.Usage
import org.gradle.api.tasks.bundling.Jar

plugins {
  `java-library`
  id("com.github.johnrengelman.shadow")
}

val shadowedJar by configurations.creating {
  isCanBeConsumed = false
  isCanBeResolved = true
  isTransitive = false
  attributes {
    attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, Usage.JAVA_RUNTIME))
    attribute(
      LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE,
      objects.named(LibraryElements::class.java, LibraryElements.JAR),
    )
    attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling::class.java, Bundling.EXTERNAL))
  }
}

dependencies {
  add(shadowedJar.name, project(":wire-grpc-client"))
  add(shadowedJar.name, libs.okhttp.core)

  api(projects.wireRuntimeJvmShaded)
  api(libs.kotlin.coroutines.core)
}

tasks.named<Jar>("jar").configure {
  enabled = false
}

val shadowJarTask = tasks.named<ShadowJar>("shadowJar")

shadowJarTask.configure {
  archiveClassifier.set("")
  configurations = listOf(shadowedJar)
  relocate("okio", "com.squareup.wire.shaded.okio")
}

configurations.named("apiElements").configure {
  attributes.attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling::class.java, Bundling.SHADOWED))
  outgoing.artifacts.clear()
  outgoing.artifact(shadowJarTask)
}

configurations.named("runtimeElements").configure {
  attributes.attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling::class.java, Bundling.SHADOWED))
  outgoing.artifacts.clear()
  outgoing.artifact(shadowJarTask)
}

tasks.named("assemble").configure {
  dependsOn(tasks.named("shadowJar"))
}
