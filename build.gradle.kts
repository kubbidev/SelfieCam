import net.fabricmc.loom.task.RemapJarTask
import org.gradle.api.tasks.testing.logging.TestLogEvent

apply("gradle/ver.gradle.kts")
plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.shadow)
    alias(libs.plugins.loom)
}

group = "me.kubbidev"

base {
    archivesName.set("selfiecam")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.10")
    mappings("net.fabricmc:yarn:1.21.10+build.2:v2")
    modImplementation("net.fabricmc:fabric-loader:0.17.3")

    val apiModules = listOf(
        "fabric-api-base",
        "fabric-key-binding-api-v1",
        "fabric-lifecycle-events-v1"
    )

    apiModules.forEach {
        modImplementation(fabricApi.module(it, "0.136.0+1.21.10"))
    }

    // Unit tests
    testImplementation("org.testcontainers:junit-jupiter:1.21.3")
    testImplementation("org.mockito:mockito-core:5.20.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.20.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:6.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.processResources {
    inputs.property("version", "$version")
    filesMatching("**/fabric.mod.json") {
        expand("version" to "$version")
    }
}

tasks.shadowJar {
    archiveFileName = "selfiecam-$version-dev.jar"
    mergeServiceFiles()
    dependencies {
        exclude("net.fabricmc:.*")
        include(dependency("me.kubbidev:.*"))

        // We don't want to include the mappings in the jar do we?
        exclude("/mappings/*")
    }
}

val remappedShadowJar by tasks.registering(RemapJarTask::class) {
    dependsOn(tasks.shadowJar)

    inputFile = tasks.shadowJar.flatMap {
        it.archiveFile
    }
    addNestedDependencies = true
    archiveFileName = "SelfieCam-Fabric-$version.jar"
}

tasks.assemble {
    dependsOn(remappedShadowJar)
}

tasks.publish {
    dependsOn(tasks.shadowJar)
}

tasks.matching { it.name.startsWith("publish") }.configureEach {
    doFirst {
        if (version.toString().contains('+')) {
            throw GradleException("Refusing to publish non-release version: $version (tag a release first)")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Test>().configureEach {
    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
    }
}

artifacts {
    archives(tasks.shadowJar)
    archives(remappedShadowJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "selfiecam"

            from(components["java"])
            pom {
                name = "SelfieCam"
                description = "A Minecraft mod that adds 2 new third-person camera modes for taking selfies!"
                url = "https://github.com/kubbidev/SelfieCam"

                licenses {
                    license {
                        name = "CC BY-NC-SA 4.0"
                        url = "https://creativecommons.org/licenses/by-nc-sa/4.0/"
                    }
                }

                developers {
                    developer {
                        id = "kubbidev"
                        name = "Kubbi"
                        url = "https://kubbidev.me"
                    }
                }

                issueManagement {
                    system = "GitHub"
                    url = "https://github.com/kubbidev/SelfieCam/issues"
                }
            }
        }
    }
    repositories {
        maven(url = "https://nexus.kubbidev.me/repository/maven-releases/") {
            name = "kubbidev-releases"
            credentials(PasswordCredentials::class) {
                username = System.getenv("GRADLE_KUBBIDEV_RELEASES_USER") ?: property("kubbidev-releases-user") as String?
                password = System.getenv("GRADLE_KUBBIDEV_RELEASES_PASS") ?: property("kubbidev-releases-pass") as String?
            }
        }
    }
}