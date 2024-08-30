package com.skillw.rpgmaker.module.scripts

import java.io.File

interface FileScript: Script {

    fun getFile(): File

}