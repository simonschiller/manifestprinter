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
}

dependencies {
    api(gradleTestKit())
    api("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    api("org.junit.jupiter:junit-jupiter-api:5.8.2")
    api("com.google.truth:truth:1.1.3")

    implementation("org.apache.velocity:velocity:1.7")
}
