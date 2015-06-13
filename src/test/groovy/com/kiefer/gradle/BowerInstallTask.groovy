package com.kiefer.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class BowerInstallTask extends PluginSupport {
    private Project project;

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"
    }

    @Test
    void tasksAreRegistered() {
        assert project.tasks.bowerInstall
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.bowerInstall
        assert project.rootDir == task.workingDir
        assert "bower" == task.executable
        assert ["install"] == task.args
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.bowerInstall
        assert task.inputs.hasInputs
        assert hasInput(task, new File(project.rootDir, 'bower.json'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'bower_components'))
    }

}