package io.github.benkiefer

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
            project.tasks.create(name: 'jsPackageInstall', type: Exec) {
                description "Install javascript dependencies"

                inputs.file "package.json"

                if (project.embercli.trackNodeModulesContents) {
                    outputs.dir "node_modules"
                }

                if(isWindows()) {
                    executable 'cmd'
                    args '/c', project.embercli.jsPackageInstallExecutable
                } else {
                    executable project.embercli.jsPackageInstallExecutable
                }

                if (project.embercli.npmRegistry) {
                    args '--registry', project.embercli.npmRegistry, project.embercli.jsPackageInstallCommand
                } else {
                    args project.embercli.jsPackageInstallCommand
                }
            }

            project.tasks.create(name: 'test', type: Exec) {
                description "Execute ember tests"

                dependsOn 'jsPackageInstall'

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

                String testCommand = project.embercli.testCommand ?: "test"
                args testCommand

                project.embercli.testArguments?.each {
                    args it
                }
            }

            project.tasks.create(name: 'emberBuild', type: Exec) {
                description "Execute ember build"

                applyAppInputs inputs

                outputs.dir "dist"

                dependsOn 'jsPackageInstall', 'test'

                if(isWindows()) {
                    executable 'cmd'
                    args '/c', findProgram(project, "ember")
                } else {
                    executable findProgram(project, "ember")
                }

                String buildCommand = project.embercli.buildCommand ?: "build"
                args buildCommand

                project.embercli.buildArguments?.each {
                    args it
                }
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
        inputs.files "app", "config", "node_modules", "public", "vendor", "Brocfile.js", "ember-cli-build.js", "package.json"
    }
}

class EmberCliPluginExtension {
    String npmRegistry
    List<String> testArguments = ["--test-port=-0"]
    List<String> buildArguments = ["--environment=production"]
    String testCommand = "test"
    String buildCommand = "build"
    String jsPackageInstallExecutable = "npm"
    String jsPackageInstallCommand = "install"
    boolean trackNodeModulesContents = true
}
