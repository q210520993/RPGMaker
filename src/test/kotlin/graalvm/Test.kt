package graalvm

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value
import org.junit.jupiter.api.Test
import java.io.InputStream

object GraalVMTest {
    @Test
    fun JSTest() {
        Context
            .newBuilder("js")
            .option("engine.WarnInterpreterOnly", "false")
            .build()
            .use { context ->
                // 执行 JavaScript 代码
                val result: Value = context.eval("js", "2 + 2")
                // 输出结果
                println("Result: ${result.asInt()}")

                // 调用 JavaScript 函数
                context.eval("js", "function add(a, b) { return a + b; }")
                val addFunction = context.getBindings("js").getMember("add")
                val sum = addFunction.execute(3, 4)
                println("Sum: ${sum.asInt()}")
        }
    }
    @Test
    fun JSFileTest() {
        val scriptStream: InputStream = javaClass.getResourceAsStream("/test.js")!!
        val scriptContent = scriptStream.bufferedReader().use { it.readText() }

        Context.create().use { context ->
            val result = context.eval("js", scriptContent)
            println("结果: ${result.asString()}")
        }
    }
}