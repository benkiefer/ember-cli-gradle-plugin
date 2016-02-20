package com.kiefer.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class BowerComponentsConfigurationTest extends EmberCliPluginSupport {
    final String PROJECT_NAME = "projectName";

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            trackBowerComponentsContents = false
        }

        project.evaluate()

    }

    @Test
    void bowerComponentsAreOutputs() {
        def task = project.tasks.bowerInstall
        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'bower_components'))

        task = project.tasks.bowerUpdate
        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'bower_components'))
    }
}
