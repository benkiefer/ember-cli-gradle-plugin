package com.kiefer.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.apache.tools.ant.util.TeeOutputStream
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.TaskInputs

class EmberCliPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("embercli", EmberCliPluginExtension)

        project.configure(project) {
            apply plugin: "distribution"

            clean {
                delete "dist", project.buildDir
                delete "tmp", project.buildDir
            }

            distributions {
                main {
                    baseName = "$project.name-$project.version"
                    contents  {
                        into "/"
                        from "dist"
                        exclude ".gitkeep"
                    }
                }
            }

            configurations {
                js
            }

            distZip {
                dependsOn "emberBuild"
                inputs.dir "dist"
                outputs.file "$project.buildDir/distributions/$project.name-${project.version}.zip"
            }

            artifacts {
                js distZip
            }

            build.dependsOn assemble
            check.dependsOn "test"
            build.dependsOn "check"
        }

        project.afterEvaluate {
            project.tasks.create(name: 'npmInstall', type: Exec) {
                description "Install npm dependencies"

                inputs.file "package.json"

                if (project.embercli.trackNodeModulesContents) {
                    outputs.dir "node_modules"
                } else {
                    outputs.file "node_modules"
                }

                if(isWindows()) {
                    executable 'cmd'
                    args '/c', 'npm'
                } else {
                    executable 'npm'
                }

                if (project.embercli.npmRegistry) {
                    args '--registry', project.embercli.npmRegistry, 'install'
                } else {
                    args 'install'
                }
            }

            project.tasks.create(name: 'bowerInstall', type: Exec) {
                description "Install bower dependencies"

                dependsOn 'npmInstall'

                inputs.file "bower.json"

                if (project.embercli.trackBowerComponentsContents) {
                    outputs.dir "bower_components"
                } else {
                    outputs.file "bower_components"
                }

                if(isWindows()) {
                    executable 'cmd'
                    args '/c', findProgram(project, "bower")
                } else {
                    executable findProgram(project, "bower")
                }

                args 'install'
            }

            project.tasks.create(name: 'bowerUpdate', type: Exec) {
                description "Update bower dependencies"

                dependsOn 'npmInstall', 'bowerInstall'

                inputs.file "bower.json"

                if (project.embercli.trackBowerComponentsContents) {
                    outputs.dir "bower_components"
                } else {
                    outputs.file "bower_components"
                }

                if(isWindows()) {
                    executable 'cmd'
                    args '/c', findProgram(project, "bower")
                } else {
                    executable findProgram(project, "bower")
                }
                args 'update'
            }

            project.tasks.create(name: 'test', type: Exec) {
                description "Execute ember tests"

                dependsOn 'npmInstall', 'bowerInstall', 'bowerUpdate'

                applyAppInputs inputs
                inputs.files "tests", "testem.json", "testem.js"
                outputs.dir "dist"
                outputs.upToDateWhen {
                    def distDir = new File(project.projectDir, "dist")
                    println distDir.absolutePath
                    distDir.exists()
                }

                outputs.upToDateWhen {
                    new File(project.projectDir, "dist").exists()
                }

                if(isWindows()) {
                    executable 'cmd'
                    args '/c', findProgram(project, "ember")
                } else {
                    executable findProgram(project, "ember")
                }

                doFirst {
                    def reportDirectory = "${project.buildDir}/reports"
                    project.delete reportDirectory
                    project.mkdir reportDirectory
                    standardOutput = new TeeOutputStream(
                            new FileOutputStream("$reportDirectory/ember-test-results.txt"), System.out);
                }

                args 'test', '--test-port=-1'
            }

            project.tasks.create(name: 'emberBuild', type: Exec) {
                description "Execute ember build"

                applyAppInputs inputs

                outputs.dir "dist"

                dependsOn 'npmInstall', 'bowerInstall', 'bowerUpdate', 'test'

                if(isWindows()) {
                    executable 'cmd'
                    args '/c', findProgram(project, "ember")
                } else {
                    executable findProgram(project, "ember")
                }

                args "build", "--environment", project.embercli.environment
            }

        }
    }

    private static Boolean isWindows() {
        return Os.isFamily(Os.FAMILY_WINDOWS);
    }

    private static String findProgram(project, String program) {
        if (Program.onPath(program)) {
            return program
        }

        return "$project.projectDir/node_modules/.bin/$program"
    }

    private static void applyAppInputs(TaskInputs inputs) {
        inputs.files "app", "config", "node_modules", "public", "vendor", "bower_components", "Brocfile.js",
            "package.json", "bower.json"
    }
}

class EmberCliPluginExtension {
    String environment = "production"
    String npmRegistry
    boolean trackNodeModulesContents = true
    boolean trackBowerComponentsContents = true
}
