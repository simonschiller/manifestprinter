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

package io.simonschiller.manifestprinter.sample.shadow

import com.google.common.truth.Truth.assertThat
import io.simonschiller.manifestprinter.fixture.Fixture
import io.simonschiller.manifestprinter.fixture.gradlew
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class SampleReflectionPluginTest {

    @TempDir
    lateinit var projectDir: Path

    @BeforeEach
    fun setup() {
        Fixture.setup("sample-shadow", projectDir)
    }

    @Test
    fun `Print task prints manifest correctly`() {
        val output = projectDir.gradlew("printPackagedClassName").output

        assertThat(output).contains("shadow.com.google.gson.Gson")
    }
}
