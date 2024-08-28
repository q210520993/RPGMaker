rootProject.name = "RPGMaker"
// settings.gradle.kts
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Test dependencies
            library("kotlinTest", "org.jetbrains.kotlin:kotlin-test:1.8.10") // 请确保使用正确的版本

            // Config dependencies
            library("snakeyaml", "org.yaml:snakeyaml:2.2")
            library("typesafeConfig", "com.typesafe:config:1.4.3")
            library("nightConfigCore", "com.electronwill.night-config:core:3.7.2")
            library("nightConfigToml", "com.electronwill.night-config:toml:3.6.7")
            library("nightConfigJson", "com.electronwill.night-config:json:3.6.7")
            library("nightConfigHocon", "com.electronwill.night-config:hocon:3.6.7")
            library("nightConfigCoreConversion", "com.electronwill.night-config:core-conversion:6.0.0")
            library("logbackClassic", "ch.qos.logback:logback-classic:1.5.6")

            // Reflex dependencies
            library("reflexAnalyser", "org.tabooproject.reflex:analyser:1.0.23")
            library("reflexFastInstanceGetter", "org.tabooproject.reflex:fast-instance-getter:1.0.23")
            library("reflex", "org.tabooproject.reflex:reflex:1.0.23")
            library("asm", "org.ow2.asm:asm:9.2")
            library("asmUtil", "org.ow2.asm:asm-util:9.2")
            library("asmCommons", "org.ow2.asm:asm-commons:9.2")

            // Guava dependency
            library("guava", "com.google.guava:guava:21.0")

            // Minestom dependencies
            library("minestomSnapshots", "net.minestom:minestom-snapshots:461c56e749")
            library("dependencyGetter", "com.github.Minestom:DependencyGetter:v1.0.1")
            library("protobufJavalite", "com.google.protobuf:protobuf-javalite:4.27.1")
            library("byteBuddyAgent", "net.bytebuddy:byte-buddy-agent:1.14.17")
            library("bytesocksJavaClient", "me.lucko:bytesocks-java-client:1.0-SNAPSHOT")
            library("minestomCeExtensions", "dev.hollowcube:minestom-ce-extensions:1.2.0")
            library("polar", "dev.hollowcube:polar:1.11.1")
            library("sparkApi", "me.lucko:spark-api:0.1-SNAPSHOT")

            // Kotlin reflection
            library("kotlinReflect", "org.jetbrains.kotlin:kotlin-reflect:1.8.10") // 请确保使用正确的版本

            // Adventure text feature pagination
            library("adventureTextFeaturePagination", "net.kyori:adventure-text-feature-pagination:4.0.0-SNAPSHOT")

            // apache
            library("apache-io", "commons-io:commons-io:2.16.1")

        }
    }
}
