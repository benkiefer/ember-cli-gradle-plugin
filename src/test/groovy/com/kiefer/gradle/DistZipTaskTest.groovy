package com.kiefer.gradle

import org.testng.annotations.Test

class DistZipTaskTest extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.distZip
    }

    @Test
    void buildDependsOn() {
        assert project.tasks.distZip.dependsOn.contains('emberBuild')
    }

}