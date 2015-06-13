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
        assert task.archiveName == "$PROJECT_NAME-${project.version}.zip"
    }

    @Test
    void dependsOn() {
        def task = project.tasks.emberPackage
        assert task.dependsOn.contains('emberBuild')
    }

}