package com.kiefer.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.Test

class EmberBuildConfigurationTest extends EmberCliPluginSupport {
    final String PROJECT_NAME = "projectName";

    @Test
    void nullingOutTheBuildCommandStillRunsBuild() {
        def project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            buildCommand = null
            buildArguments = ["--environment=production" ]
        }

        project.evaluate()

        def task = project.tasks.emberBuild
        assert project.rootDir == task.workingDir
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 5
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("build")
            assert task.args.contains("--environment=production")
        } else {
            assert task.executable.contains("ember")
            assert ["build", "--environment=production"] == task.args
        }
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            buildArguments = ["--environment=development" ]
        }

        project.evaluate()

        def task = project.tasks.emberBuild
        assert project.rootDir == task.workingDir
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 5
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("build")
            assert task.args.contains("--environment=development")

        } else {
            assert task.executable.contains("ember")
            assert ["build", "--environment=development"] == task.args
        }
    }

    @Test
    void taskExecutesWithMultipleArgs() {
        def project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            buildArguments = ["a", "b" ]
        }

        project.evaluate()

        def task = project.tasks.emberBuild
        assert project.rootDir == task.workingDir
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 5
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("build")
            assert task.args.contains("a")
            assert task.args.contains("b")

        } else {
            assert task.executable.contains("ember")
            assert ["build", "a", "b"] == task.args
        }
    }
}
