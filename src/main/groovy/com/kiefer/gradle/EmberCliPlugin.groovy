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

        project.tasks.create(name: 'npmInstall', type: Exec) {
            inputs.file "package.json"
            outputs.dir "node_modules"

            executable 'npm'
            args 'install'
        }

        project.tasks.create(name: 'test', type: Exec) {
            dependsOn 'npmInstall'
            executable 'ember'
            args 'test'
        }

        project.tasks.create(name: 'build', type: Exec) {
            inputs.files "app", "config", "node_modules", "public", "vendor", "bower_components", "Brocfile.js"

            outputs.dir "dist"

            dependsOn 'npmInstall', 'test'
            executable 'ember'
            args "build", "-prod"
        }

    }
}