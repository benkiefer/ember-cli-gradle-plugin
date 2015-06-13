package com.kiefer.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class EmberCliPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        project.task('clean') << {
            delete "$project.rootDir/tmp"
            delete "$project.rootDir/dist"
        }

    }
}