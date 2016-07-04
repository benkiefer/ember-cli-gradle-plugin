package com.kiefer.gradle

import org.testng.annotations.Test

class ProgramTest {
    @Test
    void javaIsOnPath() {
        assert Program.onPath("java")
    }
}
