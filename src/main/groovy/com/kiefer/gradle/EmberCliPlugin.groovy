package com.kiefer.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec

class EmberCliPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        project.tasks.create(name: 'clean', type: Delete) {
            delete "tmp", "dist"
        }

        project.tasks.create(name: 'build', type: Exec) {
            commandLine "ember b -prod"
        }

        project.tasks.create(name: 'test', type: Exec) {
            commandLine "ember t"
        }

    }
}