package com.kiefer.gradle

import org.testng.annotations.Test

class BuildTaskTest extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.build
    }

    @Test
    void dependsOn() {
        def task = project.tasks.build
        assert task.dependsOn.contains('emberPackage')
    }

}