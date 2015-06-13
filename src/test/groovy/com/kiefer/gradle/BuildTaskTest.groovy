package com.kiefer.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class BuildTaskTest extends PluginSupport {
    private Project project;

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"
    }

    @Test
    void tasksAreRegistered() {
        assert project.tasks.build
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.build
        assert project.rootDir == task.workingDir
        assert "ember" == task.executable
        assert ["build", "-prod"] == task.args
    }

    @Test
    void dependsOn() {
        def task = project.tasks.build
        assert task.dependsOn.contains('test')
        assert task.dependsOn.contains('npmInstall')
        assert task.dependsOn.contains('bowerInstall')
    }


    @Test
    void inputsAndOutputs() {
        def task = project.tasks.build
        assert task.inputs.hasInputs
        assert hasInput(task, new File(project.rootDir, 'Brocfile.js'))
        assert hasInput(task, new File(project.rootDir, 'config'))
        assert hasInput(task, new File(project.rootDir, 'node_modules'))
        assert hasInput(task, new File(project.rootDir, 'bower_components'))
        assert hasInput(task, new File(project.rootDir, 'app'))
        assert hasInput(task, new File(project.rootDir, 'public'))
        assert hasInput(task, new File(project.rootDir, 'vendor'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'dist'))
    }

}