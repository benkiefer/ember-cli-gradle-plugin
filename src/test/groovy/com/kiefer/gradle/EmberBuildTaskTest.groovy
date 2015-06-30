package com.kiefer.gradle

import org.testng.annotations.Test

class EmberBuildTaskTest extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.emberBuild
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.emberBuild
        assert project.rootDir == task.workingDir
        assert task.executable.contains("ember")
        assert ["build", "-prod"] == task.args
    }

    @Test
    void dependsOn() {
        def task = project.tasks.emberBuild
        assert task.dependsOn.contains('test')
        assert task.dependsOn.contains('npmInstall')
        assert task.dependsOn.contains('bowerInstall')
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.emberBuild
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