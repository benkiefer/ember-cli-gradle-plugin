package com.kiefer.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class CleanTaskTest {
    private Project project;
    private File projectDir;

    @BeforeMethod
    void setUp() {
        projectDir = File.createTempDir()
        projectDir.mkdir()

        project = ProjectBuilder.builder().withProjectDir(projectDir).build()

        project.pluginManager.apply "com.kiefer.gradle.embercli"
    }

    @AfterMethod
    void tearDown() {
        if (projectDir.exists()) {
            projectDir.delete()
        }
    }

    @Test
    void tasksAreRegistered() {
        assert project.tasks.clean
    }

    @Test
    void cleanNukesDirectories() {
        def app = project.mkdir("app")
        def tmp = project.mkdir("tmp")
        def dist = project.mkdir("dist")

        project.tasks.clean.execute()

        assert app.exists()
        assert !dist.exists()
        assert !tmp.exists()
    }


}