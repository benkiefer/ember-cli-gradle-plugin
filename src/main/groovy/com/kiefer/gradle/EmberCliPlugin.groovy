package com.kiefer.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete

class EmberCliPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        project.tasks.create(name: 'clean', type: Delete) {
            delete "tmp", "dist"
        }

    }
}