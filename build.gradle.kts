plugins {
    id("java")
    kotlin("jvm") version "1.9.22"
}

group = "com.skillw.rpgmaker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven { url = uri("https://repo.tabooproject.org/repository/releases/") }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    //config
    compileOnly("org.yaml:snakeyaml:2.2")
    compileOnly("com.typesafe:config:1.4.3")
    compileOnly("com.electronwill.night-config:core:3.6.7")
    compileOnly("com.electronwill.night-config:toml:3.6.7")
    compileOnly("com.electronwill.night-config:json:3.6.7")
    compileOnly("com.electronwill.night-config:hocon:3.6.7")
    implementation("com.electronwill.night-config:core-conversion:6.0.0")
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
    compileOnly("com.google.guava:guava:21.0")
    //minestom
    // https://mvnrepository.com/artifact/net.minestom/minestom-snapshots
    implementation("net.minestom:minestom-snapshots:edb73f0a5a")
    implementation("com.github.Minestom:DependencyGetter:v1.0.1")
    implementation("dev.hollowcube:minestom-ce-extensions:1.2.0")
    implementation(kotlin("reflect"))

}

tasks.test {
    useJUnitPlatform()
}