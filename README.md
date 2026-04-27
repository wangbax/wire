Wire
====

See the [project website][wire] for documentation and APIs.

Wire is the best solution to manage your [protobuf][1] schemas!

Fork Changes
------------

This fork is based on the `wire_5.x` branch and publishes artifacts under the
`io.github.wangbax` namespace.

It is being adapted to work with a source-level shaded Okio fork so
applications that still depend on `okio 1.x` can use Wire without depending on
the original `okio 3.x` package name at runtime.

- Wire depends on `io.github.wangbax:okio` and generated code targets
  `com.squareup.wire.shaded.okio.*`.
- gRPC API surface used by code generation is split into `wire-grpc-api` so
  codegen is less tightly coupled to the OkHttp-backed `wire-grpc-client`
  implementation.
- Generated code can target the relocated package with the CLI flag
  `--okio_package=com.squareup.wire.shaded.okio`.
- The Gradle plugin exposes the same setting via `okioPackage =
  "com.squareup.wire.shaded.okio"`.
- For client-side usage, the current integration focuses on `wire-runtime`,
  `wire-gson-support`, and `wire-moshi-adapter`.
- `wire-grpc-client` is not part of the current integration yet because
  OkHttp's public JVM APIs still expose original `okio.*` types.
- Most application consumers do not need to depend on `wire-grpc-api`
  directly; it is primarily an internal split to support generators and build
  tooling.

How to Use
----------

This fork is currently published as `5.5.1-okio-fork-2`.

If your client only uses Wire-generated `Message` types and protobuf
encoding/decoding, `wire-runtime` is enough.

If you also need Moshi or Gson adapters, add the corresponding adapter module.

Gradle

```kotlin
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath("io.github.wangbax:wire-gradle-plugin:5.5.1-okio-fork-2")
  }
}

apply(plugin = "com.squareup.wire")

dependencies {
  implementation("io.github.wangbax:wire-runtime:5.5.1-okio-fork-2")

  // Optional JSON adapters.
  // implementation("io.github.wangbax:wire-gson-support:5.5.1-okio-fork-2")
  // implementation("io.github.wangbax:wire-moshi-adapter:5.5.1-okio-fork-2")
}

wire {
  kotlin {
    okioPackage = "com.squareup.wire.shaded.okio"
  }
}
```

`wire-grpc-client` is intentionally not published in this fork yet. If your
client only needs protobuf model generation, protobuf encoding/decoding, or
JSON adapters, you do not need it.

CLI

```bash
wire-compiler \
  --proto_path=src/main/proto \
  --kotlin_out=build/generated/source/wire \
  --okio_package=com.squareup.wire.shaded.okio \
  path/to/your.proto
```

After regeneration, the generated models will import
`com.squareup.wire.shaded.okio.ByteString`, so they can run alongside
applications that still depend on `okio 1.x`.

License
--------

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://protobuf.dev/
[wire]: https://square.github.io/wire/
