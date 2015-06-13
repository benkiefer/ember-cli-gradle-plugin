package com.kiefer.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.Test

import static org.testng.Assert.assertNotNull

class EmberCliPluginTest {

    @Test
    void tasksAreRegistered() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        assertNotNull(project.tasks.clean)
    }

}