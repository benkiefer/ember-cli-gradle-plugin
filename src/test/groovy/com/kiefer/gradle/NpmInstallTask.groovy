package com.kiefer.gradle

import org.testng.annotations.Test

class NpmInstallTask extends EmberCliPluginSupport {

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