package io.github.benkiefer

import org.testng.annotations.Test

class CheckTaskTest extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.check
    }

    @Test
    void buildDependsOn() {
        assert project.tasks.check.dependsOn.contains('test')
    }

}
