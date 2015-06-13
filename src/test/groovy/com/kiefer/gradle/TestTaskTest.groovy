package com.kiefer.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class TestTaskTest {
    private Project project;

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"
    }

    @Test
    void tasksAreRegistered() {
        assert project.tasks.test
    }

    @Test
    void taskExecutesAppropriateCommand() {
        def task = project.tasks.test
        assert "ember" == task.executable
        assert ["test"] == task.args
    }

}