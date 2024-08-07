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
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    //config
    implementation("org.yaml:snakeyaml:2.2")
    implementation("com.typesafe:config:1.4.3")
    implementation("com.electronwill.night-config:core:3.7.2")
    implementation("com.electronwill.night-config:toml:3.6.7")
    implementation("com.electronwill.night-config:json:3.6.7")
    implementation("com.electronwill.night-config:hocon:3.6.7")
    implementation("com.electronwill.night-config:core-conversion:6.0.0")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    //reflex
    // 本体
    implementation("org.tabooproject.reflex:analyser:1.0.23")
    implementation("org.tabooproject.reflex:fast-instance-getter:1.0.23")
    implementation("org.tabooproject.reflex:reflex:1.0.23") // 需要 analyser 模块
    // 本体依赖
    implementation("org.ow2.asm:asm:9.2")
    implementation("org.ow2.asm:asm-util:9.2")
    implementation("org.ow2.asm:asm-commons:9.2")
    //guava
    implementation("com.google.guava:guava:21.0")
    //minestom
    // https://mvnrepository.com/artifact/net.minestom/minestom-snapshots
    implementation("net.minestom:minestom-snapshots:edb73f0a5a")
    implementation("com.github.Minestom:DependencyGetter:v1.0.1")
    implementation("com.google.protobuf:protobuf-javalite:4.27.1")
    implementation("net.bytebuddy:byte-buddy-agent:1.14.17")
    implementation("me.lucko:bytesocks-java-client:1.0-SNAPSHOT") {
        exclude("slf4j-api")
    }
    implementation("dev.hollowcube:minestom-ce-extensions:1.2.0")
    implementation("dev.hollowcube:polar:1.11.1")
    implementation(fileTree("libs"))
    //kotlin
    implementation(kotlin("reflect"))

    implementation("net.kyori:adventure-text-feature-pagination:4.0.0-SNAPSHOT") {
        exclude("adventure-api")
    }



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