package com.kiefer.gradle

import org.testng.annotations.Test

class BowerInstallTask extends EmberCliPluginSupport {

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