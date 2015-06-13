# Ember CLI Gradle Plugin
---------

WIP: See the TODO section of this README.

A plugin for building Ember CLI projects with gradle.

The plugin expects that you have a global install of npm, bower, and ember cli, which you would need if you were going to run a CLI project.

## The Clean Task
---------

An independent task that removes the `tmp` and `dist` directories.

Example:

     ./gradlew clean

## The Test Task
---------

Shells out to Ember CLI and executes the test command.

Example:

     ./gradlew test

## The Build Task
---------

Shells out to Ember CLI and executes the build command with a production environment target. Also executes the Test task.

Example:

     ./gradlew build

#TODO
---------

 - NPM Install before Test/Build
 - Up to Date Checking
 - Zip Distribution? Web Jar?
 - Finalize README
 - Publish
