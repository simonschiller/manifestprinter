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

import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.github.johnrengelman.shadow") // This plugin allows us to conveniently package dependencies
    id("maven-publish")
}

dependencies {
    compileOnly(gradleApi())

    // Implementation dependencies will be repackaged and included in the JAR
    implementation("com.google.code.gson:gson:2.9.0")

    testImplementation(project(":fixture"))
}

// Repackaging is required to avoid duplicate class errors
tasks.register("relocateShadowJar", ConfigureShadowRelocation::class.java) {
    target = tasks.shadowJar.get()
    prefix = "shadow"
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    dependsOn("relocateShadowJar")
}

tasks.withType<Test> {
    useJUnitPlatform()
    dependsOn("publishToMavenLocal") // Make plugins available in tests
}

publishing {
    publications {
        create<MavenPublication>("java") {
            shadow.component(this) // We want to publish the shadow JAR instead of the normal JAR
        }
    }
}
