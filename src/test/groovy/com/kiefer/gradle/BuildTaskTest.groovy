package com.kiefer.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class BuildTaskTest {
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
        assert task.commandLine.size() == 1
        assert "ember b -prod" == task.commandLine[0]
    }

}