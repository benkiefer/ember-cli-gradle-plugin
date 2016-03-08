package com.kiefer.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.testng.annotations.Test

class BowerUpdateTask extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.bowerUpdate
    }

    @Test
    void dependsOnNpm() {
        assert project.tasks.bowerUpdate.dependsOn.contains('npmInstall')
        assert project.tasks.bowerUpdate.dependsOn.contains('bowerInstall')
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.bowerUpdate
        assert project.rootDir == task.workingDir
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 3
            assert task.args.contains("/c")
            assert task.args.contains("bower")
            assert task.args.contains("update")
        } else {
            assert task.executable.contains("bower")
            assert ["update"] == task.args
        }
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.bowerUpdate
        assert task.inputs.hasInputs
        assert hasInput(task, new File(project.rootDir, 'bower.json'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'bower_components'))
    }

}