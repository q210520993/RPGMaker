package com.skillw.rpgmaker.core.handlers

import com.skillw.rpgmaker.core.handlers.annotations.AnnotationHandler
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.util.instance

class RegistryHandler(pack: String = "com.skillw.rpgmaker", autoInit: Boolean = true) : AnnotationHandler<AutoRegistry>(pack, autoInit) {

    override val annotation: Class<AutoRegistry> = AutoRegistry::class.java

    init {
        handleAnnotation()
        if (autoInit) {
            handle()
        }
    }

    override fun handle() {
        println(classes)
        classes.forEach {
            (it.instance as? Registrable<*>)?.register()
        }
    }

}