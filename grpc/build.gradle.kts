group = "ru.otus.grpc"
version = "1.0-SNAPSHOT"

ext["grpcVersion"] = "1.53.0"
ext["protobufVersion"] = "3.21.2"
ext["javaxAnnotationApiVersion"] = "1.2"
ext["logbackVersion"] = "1.4.6"
ext["slf4jVersion"] = "2.0.7"


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}
