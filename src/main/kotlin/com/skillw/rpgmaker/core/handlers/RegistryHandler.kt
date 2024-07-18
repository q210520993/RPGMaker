package com.skillw.rpgmaker.core.handlers

import com.skillw.rpgmaker.core.handlers.annotations.AnnotationHandler
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.util.instance
import com.skillw.rpgmaker.utils.getFieldValue

class RegistryHandler(override val autoInit: Boolean = true) : AnnotationHandler<AutoRegistry>(),AutoInit {

    override val annotation: Class<AutoRegistry> = AutoRegistry::class.java

    init {
        handleAnnotation()
        if (autoInit) {
            handle()
        }
    }

    override fun handle() {
        classes.forEach {
            if (it.getAnnotation(AutoRegistry::class.java).unsafe) {
                it.getMethod("registry").invoke(it.instance)
                return@forEach
            }
            (it.instance as? Registrable<*>)?.register()
        }
        //或许不好使，我没用过
        fields.forEach {
            it.getFieldValue<Registrable<*>>(it.declaringClass)?.register()
        }
    }

}