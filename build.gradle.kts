plugins {
    id("java")
    kotlin("jvm") version "1.9.22"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "com.skillw.rpgmaker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven { url = uri("https://repo.tabooproject.org/repository/releases/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
}

dependencies {
    // 测试依赖项
    testImplementation(libs.kotlinTest)

    // 配置相关依赖项
    implementation(libs.snakeyaml)
    implementation(libs.typesafeConfig)
    implementation(libs.nightConfigCore)
    implementation(libs.nightConfigToml)
    implementation(libs.nightConfigJson)
    implementation(libs.nightConfigHocon)
    implementation(libs.nightConfigCoreConversion)
    implementation(libs.logbackClassic)

    // Reflex 相关依赖项
    implementation(libs.reflexAnalyser)
    implementation(libs.reflexFastInstanceGetter)
    implementation(libs.reflex)
    implementation(libs.asm)
    implementation(libs.asmUtil)
    implementation(libs.asmCommons)

    // Guava 依赖项
    implementation(libs.guava)

    // Minestom 相关依赖项
    implementation(libs.minestomSnapshots)
    implementation(libs.dependencyGetter)
    implementation(libs.protobufJavalite)
    implementation(libs.byteBuddyAgent)
    implementation(libs.bytesocksJavaClient) {
        exclude("slf4j-api")
    }
    implementation(libs.minestomCeExtensions)
    implementation(libs.polar)
    implementation(libs.sparkApi)
    implementation(fileTree("libs"))

    // Kotlin 反射
    implementation(libs.kotlinReflect)

    // Adventure 文本功能分页
    implementation(libs.adventureTextFeaturePagination) {
        exclude("adventure-api")
    }

    //GraalVM
    implementation("org.graalvm.polyglot:polyglot:23.1.3")
    runtimeOnly("org.graalvm.js:js-language:23.1.3")
    runtimeOnly("org.graalvm.truffle:truffle-runtime:23.1.3")

    //apache
    implementation(libs.apache.io)
    implementation("org.jline:jline-reader:3.25.0")
    implementation("org.jline:jline-terminal:3.25.0")
    implementation("org.jline:jline-terminal-jna:3.25.0")
    implementation("org.tinylog:tinylog-api:2.7.0")
    implementation("org.tinylog:tinylog-impl:2.7.0")
    implementation("org.fusesource.jansi:jansi:2.4.1")

}
tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
    test {
        resources {
            srcDirs("src/test/resources")
        }
    }
}


tasks.build {
    dependsOn(tasks.shadowJar)
    group = "rpgmaker build"
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xpkginfo:always")
    java.sourceCompatibility = JavaVersion.VERSION_21
    java.targetCompatibility = JavaVersion.VERSION_21
}


tasks.withType<Jar> {
    manifest {
        // Change this to your main class
        attributes["Main-Class"] = "com.skillw.rpgmaker.MainKt"
    }
}

tasks.test {
    useJUnitPlatform()
}