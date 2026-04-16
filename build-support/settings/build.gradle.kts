plugins {
  `kotlin-dsl`
  `java-gradle-plugin`
}

gradlePlugin {
  plugins {
    create("wireSettings") {
      id = "com.squareup.wire.settings"
      displayName = "Wire settings plugin"
      description = "Gradle plugin for Wire build settings"
      implementationClass = "com.squareup.wire.buildsettings.WireSettingsPlugin"
    }
  }
}

repositories {
  if (System.getProperty("useMavenLocal", "false").toBoolean()) {
    mavenLocal()
  }
  mavenCentral()
  gradlePluginPortal()
  google()
}
