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

package io.simonschiller.manifestprinter.fixture

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
import java.nio.file.FileSystems
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.bufferedWriter

/**
 * Fixture to demonstrate how Gradle plugins can be tested against multiple different Android Gradle
 * plugin versions. The Android Gradle plugin version can be specified by an environment variable.
 * In combination with the CI config, this allows us to test against multiple versions in parallel.
 *
 * This sample uses a templating engine to fill the fixture with data. This is definitely overkill
 * here, but can be useful in larger projects.
 */
object Fixture {
    private val engine = VelocityEngine().apply {
        setProperty("resource.loader", "class")
        setProperty("class.resource.loader.class", ClasspathResourceLoader::class.java.name)
        init()
    }

    /** Sets up the fixture in the given [target] path. */
    fun setup(pluginName: String, target: Path) {
        val context = VelocityContext()
        context.put("SAMPLE_PLUGIN_NAME", pluginName)
        context.put("ANDROID_GRADLE_PLUGIN_VERSION", System.getenv("ANDROID_GRADLE_PLUGIN_VERSION") ?: "7.0.0")

        val resource = javaClass.getResource("")?.toURI() ?: return
        val fileSystem = FileSystems.newFileSystem(resource, mutableMapOf<String, Any>())
        Files.walkFileTree(fileSystem.getPath(""), Visitor(target, context))
    }

    /** Visitor which runs through all resources and copies them to the [target] directory. */
    private class Visitor(private val target: Path, private val context: VelocityContext) : SimpleFileVisitor<Path>() {

        override fun visitFile(file: Path, attributes: BasicFileAttributes): FileVisitResult {
            val template = engine.getTemplate(file.toString())

            val path = target.resolve(file.toString())
            Files.createDirectories(path.parent)
            path.bufferedWriter().use { writer ->
                template.merge(context, writer)
            }

            return FileVisitResult.CONTINUE
        }
    }
}
