group = "me.xra1ny.vital"
version = "1.0"

dependencies {
    implementation(project(":vital-core"))
    implementation(project(":vital-items"))
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