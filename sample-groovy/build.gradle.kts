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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.gradle.groovy")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}

dependencies {
    compileOnly(gradleApi())

    implementation(project(":common"))

    testImplementation(project(":fixture"))
}

// Include dependencies in the Groovy classpath
tasks.withType<GroovyCompile> {
    classpath = sourceSets.main.get().compileClasspath
}

// Include compiled Groovy classes in the Kotlin classpath
tasks.withType<KotlinCompile> {
    classpath += files(sourceSets.main.get().groovy.classesDirectory)
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
