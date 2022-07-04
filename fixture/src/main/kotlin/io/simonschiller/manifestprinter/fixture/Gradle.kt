/*
* Copyright 2022 Simon Schiller
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package io.simonschiller.manifestprinter.fixture

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Path

/** Runs the given Gradle [commands] in the directory specified by the receiving path. */
fun Path.gradlew(vararg commands: String): BuildResult = GradleRunner.create()
    .withProjectDir(toFile())
    .withArguments(*commands, "--stacktrace")
    .forwardOutput()
    .build()
