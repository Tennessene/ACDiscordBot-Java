plugins {
    id("java")
}

group = "com.acclash"
version = "0.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.12")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    compileJava {
        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }

    register("printVersion") {
        doLast {
            // Assuming 'project' is an instance of Project
            println(project.version)
        }
    }
}