plugins {
    java
}

dependencies {
    implementation(project(":proto"))

    implementation("io.grpc:grpc-stub:${rootProject.ext["grpcVersion"]}")
    implementation("io.grpc:grpc-protobuf:${rootProject.ext["grpcVersion"]}")
    implementation("com.google.protobuf:protobuf-java-util:${rootProject.ext["protobufVersion"]}")
    implementation("com.google.protobuf:protobuf-java:${rootProject.ext["protobufVersion"]}")

    implementation("ch.qos.logback:logback-classic:${rootProject.ext["logbackVersion"]}")
    implementation("org.slf4j:slf4j-api:${rootProject.ext["slf4jVersion"]}")


    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")

}

tasks.withType<Test> {
    useJUnitPlatform()
}