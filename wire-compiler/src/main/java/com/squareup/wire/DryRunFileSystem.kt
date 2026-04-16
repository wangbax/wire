/*
 * Copyright (C) 2023 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.wire

import com.squareup.wire.shaded.okio.FileHandle
import com.squareup.wire.shaded.okio.FileSystem
import com.squareup.wire.shaded.okio.ForwardingFileSystem
import com.squareup.wire.shaded.okio.Path
import com.squareup.wire.shaded.okio.Sink
import com.squareup.wire.shaded.okio.fakefilesystem.FakeFileSystem

/**
 * This [FileSystem] reads from its [delegate] but its writing operations do not produce anything.
 */
class DryRunFileSystem(delegate: FileSystem) : ForwardingFileSystem(delegate) {
  private val fakeFileSystem = FakeFileSystem()

  override fun sink(file: Path, mustCreate: Boolean): Sink {
    fakeFileSystem.createDirectories(file.parent!!)
    return fakeFileSystem.sink(file, mustCreate)
  }

  override fun appendingSink(file: Path, mustExist: Boolean): Sink {
    fakeFileSystem.createDirectories(file.parent!!)
    return fakeFileSystem.appendingSink(file, mustExist)
  }

  override fun openReadWrite(file: Path, mustCreate: Boolean, mustExist: Boolean): FileHandle {
    fakeFileSystem.createDirectories(file.parent!!)
    return fakeFileSystem.openReadWrite(file, mustCreate, mustExist)
  }
}
