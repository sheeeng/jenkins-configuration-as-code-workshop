#!/usr/bin/env groovy

import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

Jenkins.instance.pluginManager.plugins.stream().sorted().each{
    plugin ->
        println ("${plugin.getShortName()}:${plugin.getVersion()}")
}
