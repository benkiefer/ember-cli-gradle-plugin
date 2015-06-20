# Ember CLI Gradle Plugin
---------

A plugin for building Ember CLI projects with gradle.

The plugin expects that you have a global install of npm, bower, and ember cli, which you would need if you were going to run a CLI project.

## The Clean Task
---------

An independent task that removes the `tmp`, `build`, and `dist` directories.

Example:

     ./gradlew clean

## The NpmInstall Task
---------

Shells out to npm and executes the install command.

Example:

     ./gradlew npmInstall

## The BowerInstall Task
---------

Shells out to bower and executes the install command.

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

## The EmberPackage Task
---------

Creates a zip archive of the `dist` folder in the `build\libs` folder. This artifact is created under the js configuration.

Example:

     ./gradlew emberPackage

## The Build Task
---------

Executes the EmberPackage task and all it's dependencies. This task is the main entry point for this plugin.

Example:

     ./gradlew build

#TODO
---------

 - Finalize README
