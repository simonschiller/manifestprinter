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

package io.simonschiller.manifestprinter.sample.multiversion

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider

/** Compatibility code to retrieve the manifest on Android Gradle plugin 7.0.0. */
fun handle7Dot0(project: Project, callback: (String, Provider<RegularFile>) -> Unit) {
    val extension = project.extensions.getByType(AndroidComponentsExtension::class.java)
    extension.onVariants { variant ->
        val manifest = variant.artifacts.get(SingleArtifact.MERGED_MANIFEST)
        callback.invoke(variant.name, manifest)
    }
}
