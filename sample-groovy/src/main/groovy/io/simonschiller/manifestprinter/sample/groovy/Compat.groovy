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

import org.gradle.api.Project
import org.gradle.internal.BiAction

class Compat {

    /** Compatibility code to retrieve the manifest on Android Gradle plugin 4.1.0. */
    static void handle4Dot1(Project project, BiAction callback) {
        project.android.onVariantProperties { variant ->
            def name = "com.android.build.api.artifact.ArtifactType\$MERGED_MANIFEST"
            def enumInstance = Class.forName(name).INSTANCE
            def manifest = variant.artifacts.get(enumInstance)
            callback.execute(variant.name, manifest)
        }
    }

    /** Compatibility code to retrieve the manifest on Android Gradle plugin 4.2.0. */
    static void handle4Dot2(Project project, BiAction callback) {
        project.androidComponents {
            onVariants(selector().all()) { variant ->
                def name = "com.android.build.api.artifact.ArtifactType\$MERGED_MANIFEST"
                def enumInstance = Class.forName(name).INSTANCE
                def manifest = variant.artifacts.get(enumInstance)
                callback.execute(variant.name, manifest)
            }
        }
    }

    /** Compatibility code to retrieve the manifest on Android Gradle plugin 7.0.0. */
    static void handle7Dot0(Project project, BiAction callback) {
        project.androidComponents {
            onVariants(selector().all()) { variant ->
                def name = "com.android.build.api.artifact.SingleArtifact\$MERGED_MANIFEST"
                def enumInstance = Class.forName(name).INSTANCE
                def manifest = variant.artifacts.get(enumInstance)
                callback.execute(variant.name, manifest)
            }
        }
    }
}
