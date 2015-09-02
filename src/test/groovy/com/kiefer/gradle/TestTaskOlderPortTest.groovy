package com.kiefer.gradle

import groovy.json.JsonBuilder
import org.testng.annotations.Test

class TestTaskOlderPortTest extends EmberCliPluginSupport {
    @Override
    protected void createPackageJson() {
        JsonBuilder jsonBuilder = new JsonBuilder()
        jsonBuilder.devDependencies(
            "ember-cli": "1.13.5"
        )

        project.file("package.json") << jsonBuilder.toString()
    }

    @Test
    void taskExecutesAppropriateCommandForOlderEmberCliVersions() {
        def task = project.tasks.test

        assert task.args.contains("--port")
    }
}
