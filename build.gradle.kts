plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "BattleShip"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "16"
    modules("javafx.controls", "javafx.fxml", "javafx.media")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_16
}

dependencies {
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.flywaydb:flyway-core:7.11.0")
    implementation("net.rgielen:javafx-weaver-spring-boot-starter:1.3.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    val lombok = "org.projectlombok:lombok:1.18.20"
    compileOnly(lombok)
    annotationProcessor(lombok)
    testCompileOnly(lombok)
    testAnnotationProcessor(lombok)
}

tasks {
    compileJava {
        options.compilerArgs.addAll(
                listOf(
                        "-Xlint:deprecation",
                        "-Xlint:unchecked",
                )
        )
    }

//    jar {
//        manifest {
//            attributes(
//                    "Main-Class" to "com.lapter57.logics.Main",
//            )
//        }
//
//        from({
//            exclude("**/module-info.class")
//            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
//        })
//    }
}
