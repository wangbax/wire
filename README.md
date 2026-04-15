Wire
====

See the [project website][wire] for documentation and APIs.

Wire is the best solution to manage your [protobuf][1] schemas!

Fork Changes
------------

This fork is based on the `wire_5.x` branch and publishes artifacts under the
`io.github.wangbax` namespace.

It adds a shaded Okio integration so applications that still depend on
`okio 1.x` can use Wire without pulling in the original `okio 3.x` package at
runtime.

- Runtime artifacts such as `wire-runtime-jvm-shaded` relocate `okio.*` to
  `com.squareup.wire.shaded.okio.*`.
- Generated code can target the relocated package with the CLI flag
  `--okio_package=com.squareup.wire.shaded.okio`.
- The Gradle plugin exposes the same setting via `okioPackage =
  "com.squareup.wire.shaded.okio"`.
- Consumers should depend on the shaded runtime artifacts instead of the
  original `wire-runtime`, `wire-gson-support`, `wire-moshi-adapter`, and
  `wire-grpc-client` modules when using this forked integration.

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
