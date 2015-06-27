package com.kiefer.gradle

import org.testng.annotations.Test

class BuildTaskTest extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.build
    }

    @Test
    void buildDependsOn() {
        assert project.tasks.build.dependsOn.contains('assemble')
    }

}