package io.github.benkiefer

import org.apache.tools.ant.taskdefs.condition.Os
import org.testng.annotations.Test

class PackageInstallTaskTest extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.jsPackageInstall
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.jsPackageInstall
        assert project.rootDir == task.workingDir
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert ["/c", "npm", "install"] == task.args
        } else {
            assert "npm" == task.executable
            assert ["install"] == task.args
        }
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.jsPackageInstall
        assert task.inputs.hasInputs
        assert hasInput(task, new File(project.rootDir, 'package.json'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'node_modules'))
    }

}
