group = "me.xra1ny.vital"
version = "1.0"

dependencies {
    implementation(project(":vital-core"))

    rootProject.property("hibernate-vendor")?.let {
        implementation(it)
    }

    rootProject.property("mysql-connector-vendor")?.let {
        implementation(it)
    }
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