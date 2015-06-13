package com.kiefer.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class NpmInstallTask extends PluginSupport {
    private Project project;

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"
    }

    @Test
    void tasksAreRegistered() {
        assert project.tasks.npmInstall
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.npmInstall
        assert project.rootDir == task.workingDir
        assert "npm" == task.executable
        assert ["install"] == task.args
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.npmInstall
        assert task.inputs.hasInputs
        assert hasInput(task, new File(project.rootDir, 'package.json'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'node_modules'))
    }

}