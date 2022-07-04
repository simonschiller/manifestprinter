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

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}

// Create source sets for the different Android Gradle plugin versions
val compat4Dot1: SourceSet by sourceSets.creating
val compat4Dot2: SourceSet by sourceSets.creating
val compat7Dot0: SourceSet by sourceSets.creating

dependencies {
    compat4Dot1.compileOnlyConfigurationName(gradleApi())
    compat4Dot1.compileOnlyConfigurationName("com.android.tools.build:gradle:4.1.0")

    compat4Dot2.compileOnlyConfigurationName(gradleApi())
    compat4Dot2.compileOnlyConfigurationName("com.android.tools.build:gradle:4.2.0")

    compat7Dot0.compileOnlyConfigurationName(gradleApi())
    compat7Dot0.compileOnlyConfigurationName("com.android.tools.build:gradle:7.0.0")

    // Include the output of all compatibility source sets in the main compilation
    compileOnly(gradleApi())
    compileOnly(compat4Dot1.output)
    compileOnly(compat4Dot2.output)
    compileOnly(compat7Dot0.output)

    implementation(project(":common"))

    testImplementation(project(":fixture"))
}

// Make sure to include the output of the compatibility source sets in the final JAR
tasks.withType<Jar> {
    from(compat4Dot1.output)
    from(compat4Dot2.output)
    from(compat7Dot0.output)
}

tasks.withType<Test> {
    useJUnitPlatform()
    dependsOn("publishToMavenLocal", ":common:publishToMavenLocal") // Make plugins available in tests
}

publishing {
    publications {
        create<MavenPublication>("java") {
            from(components["java"])
        }
    }
}
