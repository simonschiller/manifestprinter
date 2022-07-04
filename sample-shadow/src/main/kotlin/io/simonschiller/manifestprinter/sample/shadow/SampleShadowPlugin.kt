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

import com.google.gson.Gson
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * This sample shows how third-party dependencies can be repackaged and shipped with Gradle plugins
 * to avoid clashes and version inconsistencies for users.
 */
class SampleShadowPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.withId("com.android.application") {
            project.tasks.register("printPackagedClassName") {
                println(Gson::class.java.name)
            }
        }
    }
}
