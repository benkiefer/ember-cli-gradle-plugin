package com.kiefer.gradle;

import java.nio.file.Files
import java.nio.file.Paths;
import java.util.regex.Pattern;

class Program {
    static boolean onPath(String program) {
        System.getenv("PATH").split(Pattern.quote(File.pathSeparator)).any {
            return Files.exists(Paths.get(it, program))
        }
    }

    private Program () {}
}
