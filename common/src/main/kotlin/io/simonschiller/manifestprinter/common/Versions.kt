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

package io.simonschiller.manifestprinter.common

import org.gradle.api.Project
import org.gradle.util.internal.VersionNumber

/** Retrieves the Android Gradle plugin version that's used in the given [project]. */
fun getAndroidGradlePluginVersion(project: Project): VersionNumber {
    return getClasspathVersion(project, "com.android.tools.build:gradle")
}

/**
 * Retrieves the version resolved by Gradle for the given [project] with the given [identifier].
 * This method of checking versions can be used for all plugins/dependencies.
 */
fun getClasspathVersion(project: Project, identifier: String): VersionNumber {
    val buildscript = project.rootProject.buildscript
    val config = buildscript.configurations.getByName("classpath")
    val artifacts = config.resolvedConfiguration.resolvedArtifacts

    val artifact = artifacts.singleOrNull { artifact ->
        artifact.moduleVersion.id.toString().startsWith("$identifier:")
    } ?: error("Could not determine version of artifact with identifier '$identifier'")

    val version = artifact.moduleVersion.id.version
    return VersionNumber.parse(version)
}
