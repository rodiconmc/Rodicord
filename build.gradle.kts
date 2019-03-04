
import com.novoda.gradle.release.PublishExtension

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("com.novoda:bintray-release:0.9")
    }
}

apply(plugin = "com.novoda.bintray-release")


plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.jetbrains:annotations:17.0.0")
    testImplementation("junit:junit:4.12")
}

configure<PublishExtension> {
    userOrg = "rodiconmc"
    repoName = "RodiconRepo"
    groupId = "com.rodiconmc"
    artifactId = "Rodicord"
    publishVersion = "0.2.0"
    desc = "Rodicord is a library for manipulating the Discord Api via a distributed system."
    website = "https://github.com/rodiconmc/Rodicord"
}
