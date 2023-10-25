group = "me.xra1ny.vital"
version = "1.0"

plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version("8.1.1")
}

dependencies {
    implementation(project(":vital-core"))
    implementation(project(":vital-configs"))
    implementation(project(":vital-commands"))
    implementation(project(":vital-holograms"))
    implementation(project(":vital-items"))
    implementation(project(":vital-tasks"))
    implementation(project(":vital-inventories"))
    implementation(project(":vital-scoreboards"))
    implementation(project(":vital-players"))
    implementation(project(":vital-minigames"))
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

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.publishToMavenLocal {
    dependsOn(tasks.shadowJar)
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenLocal()
        mavenCentral()

        // PaperMC
        rootProject.property("paper-repo")?.let {
            maven(it)
        }
    }

    dependencies {
        // Jetbrains Annotations
        rootProject.property("annotations-vendor")?.let {
            implementation(it)
            testImplementation(it)
        }

        // Lombok
        rootProject.property("lombok-vendor")?.let {
            compileOnly(it)
            testCompileOnly(it)
            annotationProcessor(it)
            testAnnotationProcessor(it)
        }

        // Reflections
        rootProject.property("reflection-vendor")?.let {
            implementation(it)
            testImplementation(it)
        }

        // PaperMC
        rootProject.property("paper-vendor")?.let {
            compileOnly(it)
            testCompileOnly(it)
        }
    }

    tasks.build {
        dependsOn(tasks.shadowJar)
    }
}