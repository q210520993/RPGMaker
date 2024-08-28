package com.skillw.rpgmaker.module.system.spark

import me.lucko.spark.common.sampler.source.ClassSourceLookup

class ClassLookup(classLoader: ClassLoader): ClassSourceLookup.ByClassLoader() {
    var CLASS: Class<*> = classLoader::class.java

    override fun identify(p0: ClassLoader): String? {
        if (CLASS.isInstance(p0)) {
            return p0.name
        }
        return null
    }
}