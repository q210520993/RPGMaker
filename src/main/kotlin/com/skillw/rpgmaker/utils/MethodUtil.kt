package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.util.instance
import com.skillw.rpgmaker.utils.ClassUtil.isSingle
import java.lang.reflect.Method

fun Method.run(vararg args: Any) {
    if (this.declaringClass.isSingle()) {
        this.invoke(this.declaringClass.instance, *args)
        return
    }
    this.invoke(null, *args)
}