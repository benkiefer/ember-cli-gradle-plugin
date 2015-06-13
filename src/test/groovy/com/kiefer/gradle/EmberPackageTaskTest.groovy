package com.kiefer.gradle

import org.testng.annotations.Test

class EmberPackageTaskTest extends EmberCliPluginSupport {
    @Test
    void tasksAreRegistered() {
        assert project.tasks.emberPackage
    }

    @Test
    void zipIsConfiguredCorrectly() {
        def task = project.tasks.emberPackage
        assert task.archivePath == new File("${project.buildDir}/libs/$task.archiveName")
    }

    @Test
    void dependsOn() {
        def task = project.tasks.emberPackage
        assert task.dependsOn.contains('emberBuild')
    }

}