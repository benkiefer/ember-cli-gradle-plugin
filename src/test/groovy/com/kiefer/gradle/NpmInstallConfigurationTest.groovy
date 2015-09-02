package com.kiefer.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class NpmInstallConfigurationTest extends EmberCliPluginSupport {
    final String PROJECT_NAME = "projectName";

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            npmRegistry = "foo"
        }

        project.evaluate()
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.npmInstall
        assert ["--registry", "foo", "install"] == task.args
    }
}
