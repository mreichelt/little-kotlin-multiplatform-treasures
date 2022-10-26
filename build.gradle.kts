allprojects {
    repositories {
        mavenCentral()
    }
}

plugins {
    `maven-publish`
}

val projectsToPublish = allprojects.filter { project ->
    project.name in listOf("featureA", "featureB", "featureC", "06_publish")
}

configure(projectsToPublish) {
    group = "com.example.droidcon"
    version = "1.0.0-SNAPSHOT"

    afterEvaluate {
        publishing {
            repositories {
                maven {
                    name = "droidconrepo"
                    url = uri("http://localhost:8081/artifactory/droidcon-maven")
                    credentials {
                        username = System.getenv("DROIDCON_USERNAME") ?: ""
                        password = System.getenv("DROIDCON_PASSWORD") ?: ""
                    }
                    isAllowInsecureProtocol = true
                }
            }
        }
    }
}
