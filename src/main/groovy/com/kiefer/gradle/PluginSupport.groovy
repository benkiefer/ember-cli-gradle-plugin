package com.kiefer.gradle

import org.gradle.api.Task

abstract class PluginSupport {
    protected boolean hasInput(Task task, File file) {
        task.inputs.files.contains(file)
    }

    protected boolean hasOutput(Task task, File file) {
        task.outputs.files.contains(file)
    }

}
