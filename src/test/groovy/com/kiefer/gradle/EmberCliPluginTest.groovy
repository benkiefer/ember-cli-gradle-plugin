package com.kiefer.gradle

import org.testng.annotations.Test

class EmberCliPluginTest extends EmberCliPluginSupport {

    @Test
    void buildDirectoryIsConfigured() {
        assert project.buildDir == new File(project.rootDir, "build")
    }

    @Test
    void jsConfigurationExists() {
        assert project.configurations.js
    }
}
