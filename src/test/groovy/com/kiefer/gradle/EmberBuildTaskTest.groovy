package com.kiefer.gradle

import org.apache.tools.ant.taskdefs.condition.Os
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
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 5
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("build")
            assert task.args.contains("--environment")
            assert task.args.contains("production")
        } else {
            assert task.executable.contains("ember")
            assert ["build", "--environment=production"] == task.args
        }
    }

    @Test
    void dependsOn() {
        def task = project.tasks.emberBuild
        assert task.dependsOn.contains('test')
        assert task.dependsOn.contains('npmInstall')
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.emberBuild
        assert task.inputs.hasInputs
        assert hasInput(task, new File(project.rootDir, 'Brocfile.js'))
        assert hasInput(task, new File(project.rootDir, 'config'))
        assert hasInput(task, new File(project.rootDir, 'node_modules'))
        assert hasInput(task, new File(project.rootDir, 'app'))
        assert hasInput(task, new File(project.rootDir, 'public'))
        assert hasInput(task, new File(project.rootDir, 'vendor'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'dist'))
    }

}
