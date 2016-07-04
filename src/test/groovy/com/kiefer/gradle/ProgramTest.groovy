package com.kiefer.gradle

import org.testng.annotations.Test

class ProgramTest {
    @Test
    void javaIsOnPathgi() {
        assert Program.onPath("java")
    }
}
