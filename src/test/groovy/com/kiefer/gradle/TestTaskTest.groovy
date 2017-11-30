package com.kiefer.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.Test

class TestTaskTest extends EmberCliPluginSupport {
    @Test
    void tasksAreRegistered() {
        assert project.tasks.test
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.test
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 4
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("test")
            assert task.args.contains("--test-port=-0")
        } else {
            assert task.executable.contains("ember")
            assert task.args.size() == 2
            assert task.args.contains("test")
            assert task.args.contains("--test-port=-0")
        }
    }

    @Test
    void taskPassesTestArgumentsWhenPresent() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            testArguments = ["foo"]
        }

        project.evaluate()

        def task = project.tasks.test
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 4
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("test")
            assert task.args.contains("foo")
        } else {
            assert task.executable.contains("ember")
            assert task.args.size() == 2
            assert task.args.contains("test")
            assert task.args.contains("foo")
        }
    }

    @Test
    void taskWithNullTestArguments() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            testArguments = null
        }

        project.evaluate()

        def task = project.tasks.test
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 3
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("test")
        } else {
            assert task.executable.contains("ember")
            assert task.args.size() == 1
            assert task.args.contains("test")
        }
    }

    @Test
    void taskWithEmptyArgs() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            testArguments = []
        }

        project.evaluate()

        def task = project.tasks.test
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 3
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("test")
        } else {
            assert task.executable.contains("ember")
            assert task.args.size() == 1
            assert task.args.contains("test")
        }
    }

    @Test
    void taskWithMultipleArgs() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            testArguments = ["a", "b"]
        }

        project.evaluate()

        def task = project.tasks.test
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 5
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("test")
            assert task.args.contains("a")
            assert task.args.contains("b")
        } else {
            assert task.executable.contains("ember")
            assert task.args.size() == 3
            assert task.args.contains("test")
            assert task.args.contains("a")
            assert task.args.contains("b")
        }
    }

    @Test
    void testCommandIsConfigurable() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            testCommand = "exam"
        }

        project.evaluate()

        def task = project.tasks.test
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 4
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("exam")
            assert task.args.contains("--test-port=-0")
        } else {
            assert task.executable.contains("ember")
            assert task.args.size() == 2
            assert task.args.contains("exam")
            assert task.args.contains("--test-port=-0")
        }
    }

    @Test
    void testCommandWillReplaceNullWithTest() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"

        project.embercli {
            testCommand = null
        }

        project.evaluate()

        def task = project.tasks.test
        if(Os.isFamily(Os.FAMILY_WINDOWS)) {
            assert "cmd" == task.executable
            assert task.args.size() == 4
            assert task.args.contains("/c")
            assert task.args.contains("ember")
            assert task.args.contains("test")
            assert task.args.contains("--test-port=-0")
        } else {
            assert task.executable.contains("ember")
            assert task.args.size() == 2
            assert task.args.contains("test")
            assert task.args.contains("--test-port=-0")
        }
    }

    @Test
    void dependsOn() {
        def task = project.tasks.test
        assert task.dependsOn.contains('jsPackageInstall')
    }

    @Test
    void inputsAndOutputs() {
        def task = project.tasks.test
        assert task.inputs.hasInputs

        assert hasInput(task, new File(project.rootDir, 'Brocfile.js'))
        assert hasInput(task, new File(project.rootDir, 'ember-cli-build.js'))
        assert hasInput(task, new File(project.rootDir, 'package.json'))
        assert hasInput(task, new File(project.rootDir, 'config'))
        assert hasInput(task, new File(project.rootDir, 'node_modules'))
        assert hasInput(task, new File(project.rootDir, 'app'))
        assert hasInput(task, new File(project.rootDir, 'public'))
        assert hasInput(task, new File(project.rootDir, 'vendor'))
        assert hasInput(task, new File(project.rootDir, 'tests'))
        assert hasInput(task, new File(project.rootDir, 'testem.json'))
        assert hasInput(task, new File(project.rootDir, 'testem.js'))

        assert task.outputs.hasOutput
        assert hasOutput(task, new File(project.rootDir, 'dist'))
    }

}
