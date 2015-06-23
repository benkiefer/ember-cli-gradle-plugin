package com.kiefer.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.TaskInputs
import org.gradle.api.tasks.bundling.Zip

class EmberCliPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.buildDir = "$project.rootDir/build"
        project.mkdir(project.buildDir)
        project.mkdir(new File(project.buildDir, "libs"))

        project.tasks.create(name: 'clean', type: Delete) {
            description "Remove ember cli dist and tmp directories"
            delete "dist", project.buildDir
        }

        project.tasks.create(name: 'npmInstall', type: Exec) {
            description "Install npm dependencies"

            inputs.file "package.json"
            outputs.dir "node_modules"

            executable 'npm'
            args 'install'
        }

        project.tasks.create(name: 'bowerInstall', type: Exec) {
            description "Install bower dependencies"

            inputs.file "bower.json"
            outputs.dir "bower_components"

            executable 'bower'
            args 'install'
        }

        project.tasks.create(name: 'test', type: Exec) {
            description "Execute ember tests"

            dependsOn 'npmInstall', 'bowerInstall'

            applyAppInputs inputs
            inputs.files "tests", "testem.json"
            outputs.dir "dist"

            executable 'ember'
            args 'test'
        }

        project.tasks.create(name: 'emberBuild', type: Exec) {
            description "Execute ember build"

            applyAppInputs inputs

            outputs.dir "dist"

            dependsOn 'npmInstall', 'bowerInstall', 'test'
            executable 'ember'
            args "build", "-prod"
        }

        project.tasks.create(name: 'emberPackage', type: Zip) {
            description "Build the zip distribution of the ember application"
            dependsOn "emberBuild"

            baseName = project.name
            version = project.version

            from 'dist'
            destinationDir = new File("${project.buildDir}/libs")
        }

        project.tasks.create(name: 'build') {
            description "Execute the full ember build lifecycle"
            dependsOn "emberPackage"
        }

        project.configurations {
            js
        }

        project.artifacts {
            js project.tasks.emberPackage
        }

    }

    private void applyAppInputs(TaskInputs inputs) {
        inputs.files "app", "config", "node_modules", "public", "vendor", "bower_components", "Brocfile.js"
    }
}