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

package io.simonschiller.manifestprinter.sample.reflection

import io.simonschiller.manifestprinter.common.PrintManifestTask
import io.simonschiller.manifestprinter.common.getAndroidGradlePluginVersion
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.util.internal.VersionNumber

/**
 * This sample shows how reflection can be used to write code that is compatible with multiple
 * Android Gradle plugin versions.
 */
@Suppress("DuplicatedCode")
class SampleReflectionPlugin : Plugin<Project> {

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

    private fun registerTask(project: Project, callback: (String, Provider<RegularFile>) -> Unit) {
        val version = getAndroidGradlePluginVersion(project)
        when {
            version < VersionNumber.parse("4.2.0") -> handle4Dot1(project, callback)
            version < VersionNumber.parse("7.0.0") -> handle4Dot2(project, callback)
            else -> handle7Dot0(project, callback)
        }
    }

    /** Compatibility code to retrieve the manifest on Android Gradle plugin 4.1.0. */
    @Suppress("UNCHECKED_CAST")
    private fun handle4Dot1(project: Project, callback: (String, Provider<RegularFile>) -> Unit) {
        val android = project.extensions.getByName("android")
        val method = android::class.java.getMethod("onVariantProperties", Action::class.java)
        method.invoke(android, Action<Any> { variant ->
            val name = variant::class.java.getMethod("getName").invoke(variant) as String

            val className = "com.android.build.api.artifact.ArtifactType"
            val enumClass = Class.forName("$className\$MERGED_MANIFEST")
            val enumInstance = enumClass.getField("INSTANCE").get(null)

            val artifacts = variant::class.java.getMethod("getArtifacts").invoke(variant)
            val getMethod = artifacts::class.java.getMethod("get", Class.forName(className))
            val manifest = getMethod.invoke(artifacts, enumInstance) as Provider<RegularFile>

            callback.invoke(name, manifest)
        })
    }

    /** Compatibility code to retrieve the manifest on Android Gradle plugin 4.2.0. */
    @Suppress("UNCHECKED_CAST")
    private fun handle4Dot2(project: Project, callback: (String, Provider<RegularFile>) -> Unit) {
        val android = project.extensions.getByName("androidComponents")

        val selectorClass = Class.forName("com.android.build.api.extension.VariantSelector")
        val selector = android::class.java.getMethod("selector").invoke(android)
        val all = selector::class.java.getMethod("all").invoke(selector)

        val method = android::class.java.getMethod("onVariants", selectorClass, Action::class.java)
        method.invoke(android, all, Action<Any> { variant ->
            val name = variant::class.java.getMethod("getName").invoke(variant) as String

            val className = "com.android.build.api.artifact.ArtifactType"
            val enumClass = Class.forName("$className\$MERGED_MANIFEST")
            val enumInstance = enumClass.getField("INSTANCE").get(null)

            val artifacts = variant::class.java.getMethod("getArtifacts").invoke(variant)
            val getMethod = artifacts::class.java.getMethod("get", Class.forName(className))
            val manifest = getMethod.invoke(artifacts, enumInstance) as Provider<RegularFile>

            callback.invoke(name, manifest)
        })
    }

    /** Compatibility code to retrieve the manifest on Android Gradle plugin 7.0.0. */
    @Suppress("UNCHECKED_CAST")
    private fun handle7Dot0(project: Project, callback: (String, Provider<RegularFile>) -> Unit) {
        val android = project.extensions.getByName("androidComponents")

        val selectorClass = Class.forName("com.android.build.api.extension.VariantSelector")
        val selector = android::class.java.getMethod("selector").invoke(android)
        val all = selector::class.java.getMethod("all").invoke(selector)

        val method = android::class.java.getMethod("onVariants", selectorClass, Action::class.java)
        method.invoke(android, all, Action<Any> { variant ->
            val name = variant::class.java.getMethod("getName").invoke(variant) as String

            val className = "com.android.build.api.artifact.SingleArtifact"
            val enumClass = Class.forName("$className\$MERGED_MANIFEST")
            val enumInstance = enumClass.getField("INSTANCE").get(null)

            val artifacts = variant::class.java.getMethod("getArtifacts").invoke(variant)
            val getMethod = artifacts::class.java.getMethod("get", Class.forName(className))
            val manifest = getMethod.invoke(artifacts, enumInstance) as Provider<RegularFile>

            callback.invoke(name, manifest)
        })
    }
}
