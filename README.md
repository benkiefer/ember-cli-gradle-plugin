# Ember CLI Gradle Plugin
---------

A plugin for building Ember CLI projects with gradle.

The plugin expects that you have a global install of npm, bower, and ember cli, which you would need if you were going to run a CLI project.

Note: This plugin uses the Distribution plugin under the hood to create its zip artifact, so it will inherit tasks from that plugin.

## Requirements
---------

 - A build task is necessary for this plugin to work. If you use Gradle 2.4 or later, you get this task for free as part of the default build lifecycle
 - Java 8 or later

## Usage
---------

In your build.gradle file...

     buildscript {
         repositories {
             jcenter()
         }
         dependencies {
             classpath "com.kiefer.gradle:ember-cli-gradle-plugin:VERSION_NUMBER"
         }
     }

     apply plugin: "com.kiefer.gradle.embercli"

Then run:

     ./gradlew clean build

For a more complete example including extraction into a project, check out my [sample project](https://github.com/benkiefer/gradle-ember-cli-example).

## The Clean Task
---------

An independent task that removes the `dist` directories.

Example:

     ./gradlew clean

## The NpmInstall Task
---------

Shells out to npm and executes the install command.

Example:

     ./gradlew npmInstall

## The BowerInstall Task
---------

Executes the bower install command. Also executes the NpmInstall task to ensure bower is available.

Example:

     ./gradlew bowerInstall

## The Test Task
---------

Shells out to Ember CLI and executes the test command. Also executes the BowerInstall and NpmInstall tasks.

Example:

     ./gradlew test

## The EmberBuild Task
---------

Shells out to Ember CLI and executes the build command with a production environment target. Also executes the Test, NpmInstall, and BowerInstall task.

Example:

     ./gradlew emberBuild
