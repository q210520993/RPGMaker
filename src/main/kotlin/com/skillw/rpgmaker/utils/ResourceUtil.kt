package com.skillw.rpgmaker.utils

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.net.URISyntaxException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes



object ResourceUtil {

    /**
     * 从资源中提取内容到目标路径。
     *
     * @param source 要提取的资源路径
     * @throws URISyntaxException 如果资源路径格式不正确时抛出
     * @throws IOException 如果操作过程中发生 IO 错误时抛出
     */
    @Throws(URISyntaxException::class, IOException::class)
    fun extractResource(source: String) {
        // 获取资源的 URI
        val uri = Thread.currentThread().contextClassLoader.getResource(source)?.toURI()
        var fileSystem: FileSystem? = null
        // 如果资源位于 JAR 文件中，则创建新的文件系统
        if (uri.toString().startsWith("jar:")) {
            fileSystem = FileSystems.newFileSystem(uri, mapOf("create" to "true"))
        }

        try {
            // 获取资源的路径
            val jarPath = uri?.let { Paths.get(it) }
            val target = Paths.get(source)

            // 如果目标路径已经存在，则不覆盖
            if (!Files.exists(target)) {
                // 复制文件和目录到目标路径
                jarPath?.let {
                    Files.walkFileTree(it, object : SimpleFileVisitor<Path>() {
                        override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                            val currentTarget = target.resolve(jarPath.relativize(dir).toString())
                            Files.createDirectories(currentTarget)
                            return FileVisitResult.CONTINUE
                        }

                        override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                            val to = target.resolve(jarPath.relativize(file).toString())
                            Files.copy(file, to, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING)
                            return FileVisitResult.CONTINUE
                        }
                    })
                }
            }
        } finally {
            // 关闭文件系统
            fileSystem?.close()
        }
    }

    fun listFilesInResourcesFolder(fileName: String): File? {
        // 获取当前类的类加载器
        val classLoader = Thread.currentThread().contextClassLoader

        // 获取资源文件夹下的所有文件
        val resourceFolderURL = classLoader.getResource(fileName)

        return resourceFolderURL?.let {
            File(it.file)
        }
    }

}
