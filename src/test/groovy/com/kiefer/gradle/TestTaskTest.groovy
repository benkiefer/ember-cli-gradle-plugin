package com.kiefer.gradle

import org.testng.annotations.Test

class TestTaskTest extends EmberCliPluginSupport{

    @Test
    void tasksAreRegistered() {
        assert project.tasks.test
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.test
        assert task.executable.contains("ember")
        assert ["test"] == task.args
    }

    @Test
    void dependsOn() {
        def task = project.tasks.test
        assert task.dependsOn.contains('npmInstall')
        assert task.dependsOn.contains('bowerInstall')
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.test
        assert task.inputs.hasInputs

        assert hasInput(task, new File(project.rootDir, 'Brocfile.js'))
        assert hasInput(task, new File(project.rootDir, 'config'))
        assert hasInput(task, new File(project.rootDir, 'node_modules'))
        assert hasInput(task, new File(project.rootDir, 'bower_components'))
        assert hasInput(task, new File(project.rootDir, 'app'))
        assert hasInput(task, new File(project.rootDir, 'public'))
        assert hasInput(task, new File(project.rootDir, 'vendor'))
        assert hasInput(task, new File(project.rootDir, 'tests'))
        assert hasInput(task, new File(project.rootDir, 'testem.json'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'dist'))
    }

}