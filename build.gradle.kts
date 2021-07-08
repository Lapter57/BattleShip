plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "BattleShip"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("logics.Main")
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
    implementation("mysql:mysql-connector-java:5.1.44")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

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
}

tasks.jar {
    manifest {
        attributes(
                "Main-Class" to "logics.Main",
        )
    }

    from({
        exclude("**/module-info.class")
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}