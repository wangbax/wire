plugins {
  id("java-platform")
}

// TODO(Benoit) Find why collectBomConstraints() is causing troubles for consumers using the BOM.
//  I'm seeing many like `:<project>: Could not find com.squareup.wire:wire-compiler:.` etc.
dependencies {
  constraints {
    api(projects.wireCompiler)
    api(projects.wireGradlePlugin)
    api(projects.wireGrpcApi)
    api(projects.wireGrpcApi.group + ":wire-grpc-api-jvm:" + projects.wireGrpcApi.version)
    api(projects.wireGsonSupport)
    api(projects.wireJavaGenerator)
    api(projects.wireKotlinGenerator)
    api(projects.wireMoshiAdapter)
    api(projects.wireRuntime)
    api(projects.wireRuntime.group + ":wire-runtime-jvm:" + projects.wireRuntime.version)
    api(projects.wireSchema)
    api(projects.wireSchema.group + ":wire-schema-jvm:" + projects.wireSchema.version)
  }
}
