#!/usr/bin/env groovy

import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

println "▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣"
println "Print the name of all jobs including jobs inside a folder and the folders themselves."
println ""

Jenkins.instance.getAllItems(AbstractItem.class).each {
    println(it.fullName)
};

println "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"
println "Print the name of all jobs including jobs inside a folder, but not the folders themselves."
println ""

Jenkins.instance.getAllItems(Job.class).each{
    println it.name + " - " + it.class
}

println "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"
println "Print the name of all jobs implementing the AbstractProject class, i.e. Freestyle and Maven jobs."
println ""

Jenkins.instance.getAllItems(AbstractProject.class).each {it ->
    println it.fullName;
}

println "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"
println "Print the name of all items inside a view."
println ""
hudson.model.Hudson.instance.getView('All').items.each() {
    println it.fullDisplayName
}

println "▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣▣"
