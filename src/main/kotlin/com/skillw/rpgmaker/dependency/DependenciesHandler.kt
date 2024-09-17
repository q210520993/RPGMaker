package com.skillw.rpgmaker.dependency

import com.skillw.rpgmaker.core.handlers.annotations.AnnotationHandler
import net.minestom.dependencies.maven.MavenRepository
import net.minestom.dependencies.maven.MavenResolver
import java.io.File

private val file = File("libs")

class DependenciesHandler: AnnotationHandler<RuntimeDependencies>() {
    override val annotation: Class<RuntimeDependencies> = RuntimeDependencies::class.java
    init {
        handleAnnotation()
        handle()
    }
    override fun handle() {
        println(classes)
        classes.forEach { clazz ->
            val repositoryURL = clazz.getAnnotation(RuntimeDependencies::class.java).repository
            val repositories = ArrayList<MavenRepository>()
            repositoryURL.forEach {
                val r = it.split(":", limit = 2)
                if(r.size < 2) {
                    return
                }
                println(r)
                repositories.add(MavenRepository(r[0], r[1]))
            }
            val mavenResolver = MavenResolver(repositories)
            clazz.getAnnotation(RuntimeDependencies::class.java).value.forEach {
                val ed = mavenResolver.resolve(it.name, file)
                ed.printTree()
            }
        }
    }


}