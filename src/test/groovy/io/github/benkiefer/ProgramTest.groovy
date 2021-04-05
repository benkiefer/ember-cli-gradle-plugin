package io.github.benkiefer

import io.github.benkiefer.Program
import org.testng.annotations.Test

class ProgramTest {
    @Test
    void javaIsOnPath() {
        assert Program.onPath("java")
    }
}
