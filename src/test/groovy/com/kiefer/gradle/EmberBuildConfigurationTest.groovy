package com.kiefer.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class EmberBuildConfigurationTest extends EmberCliPluginSupport {
    final String PROJECT_NAME = "projectName";

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            environment = "development"
        }

        project.evaluate()
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.emberBuild
        assert project.rootDir == task.workingDir
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 5
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("build")
            assert task.args.contains("--environment")
            assert task.args.contains("development")
        } else {
            assert task.executable.contains("ember")
            assert ["build", "--environment", "development"] == task.args
        }
    }
}
