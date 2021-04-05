package io.github.benkiefer

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class PackageInstallConfigurationTest extends EmberCliPluginSupport {
    final String PROJECT_NAME = "projectName";

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "io.github.benkiefer.embercli"

        project.embercli {
            npmRegistry = "foo"
            trackNodeModulesContents = true
        }

        project.evaluate()

    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.jsPackageInstall
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert ["/c", "npm", "--registry", "foo", "install"] == task.args
        } else {
            assert ["--registry", "foo", "install"] == task.args
        }
    }

    @Test
    void nodeModulesAreOutputs() {
        def task = project.tasks.jsPackageInstall
        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'node_modules'))
    }
}
