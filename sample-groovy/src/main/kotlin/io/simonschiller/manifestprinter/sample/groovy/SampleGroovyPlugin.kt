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

package io.simonschiller.manifestprinter.sample.groovy

import io.simonschiller.manifestprinter.common.PrintManifestTask
import io.simonschiller.manifestprinter.common.getAndroidGradlePluginVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.internal.BiAction
import org.gradle.util.internal.VersionNumber

/**
 * This sample shows how a dynamically typed language like Groovy can be used to write code that is
 * compatible with multiple Android Gradle plugin versions.
 */
class SampleGroovyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.withId("com.android.application") {
            registerTask(project) { variant, manifest ->
                val name = "print${variant.capitalized()}Manifest"
                project.tasks.register(name, PrintManifestTask::class.java) { task ->
                    task.manifest.set(manifest)
                }
            }
        }
    }

    private fun registerTask(project: Project, callback: BiAction<String, Provider<RegularFile>>) {
        val version = getAndroidGradlePluginVersion(project)
        when {
            version < VersionNumber.parse("4.2.0") -> Compat.handle4Dot1(project, callback)
            version < VersionNumber.parse("7.0.0") -> Compat.handle4Dot2(project, callback)
            else -> Compat.handle7Dot0(project, callback)
        }
    }
}
