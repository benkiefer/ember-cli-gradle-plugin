package com.kiefer.gradle

import groovy.json.JsonBuilder
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod

abstract class EmberCliPluginSupport {
    protected Project project;
    protected final String PROJECT_NAME = "projectName";

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.pluginManager.apply "com.kiefer.gradle.embercli"
        createPackageJson();
        project.evaluate()
    }

    protected void createPackageJson() {
        JsonBuilder jsonBuilder = new JsonBuilder()
        jsonBuilder.devDependencies(
            "ember-cli": "0.0.0"
        )

        project.file("package.json") << jsonBuilder.toString()
    }

    protected boolean hasInput(Task task, File file) {
        task.inputs.files.contains(file)
    }

    protected boolean hasOutput(Task task, File file) {
        task.outputs.files.contains(file)
    }

}
