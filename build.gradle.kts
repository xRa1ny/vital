plugins {
    java
    `maven-publish`
}

dependencies {
    compileOnly(project(":vital-core"))
    compileOnly("me.xra1ny.essentia:essentia-configure:1.0")
    implementation(project(":vital-core-processor"))
    implementation(project(":vital-commands-processor"))
}

tasks.javadoc {
    (options as StandardJavadocDocletOptions)
            .tags(
                    "apiNote:a:API Note:",
                    "implSpec:a:Implementation Requirements:",
                    "implNote:a:Implementation Note:"
            )
}

allprojects {
    group = "me.xra1ny.vital"
    version = "1.0"

    apply<JavaPlugin>()
    apply<MavenPublishPlugin>()

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.28")
        annotationProcessor("org.projectlombok:lombok:1.18.28")
        implementation("org.reflections:reflections:0.10.2")
        compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
        implementation("me.xra1ny.essentia:essentia-inject:1.0")
        implementation("me.xra1ny.essentia:essentia-except:1.0")
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = group.toString()
                artifactId = project.name
                version = version

                from(components["java"])
            }
        }
    }

    tasks.javadoc {
        (options as StandardJavadocDocletOptions)
                .tags(
                        "apiNote:a:API Note:",
                        "implSpec:a:Implementation Requirements:",
                        "implNote:a:Implementation Note:"
                )
    }
}