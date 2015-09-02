package com.kiefer.gradle

import groovy.json.JsonBuilder
import org.testng.annotations.Test

class TestTaskNewerPortTest extends EmberCliPluginSupport {
    @Override
    protected void createPackageJson() {
        JsonBuilder jsonBuilder = new JsonBuilder()
        jsonBuilder.devDependencies(
            "ember-cli": "1.13.6"
        )

        project.file("package.json") << jsonBuilder.toString()
    }

    @Test
    void taskExecutesAppropriateCommandForNewerEmberCliVersions() {
        def task = project.tasks.test

        assert task.args.contains("--test-port")
    }
}
