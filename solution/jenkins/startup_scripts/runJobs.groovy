import jenkins.model.Jenkins


println '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
def seedJobRepository = Jenkins.get().getItem('germ').getItem('seed-job')
// println seedJobRepository.getClass()
// class org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
//
// println '~~~ Dumping seed job repository object....'
// println seedJobRepository.dump()

// Execute and wait for `Scan Multibranch Pipeline Now` to be finished.
// The getFuture().get() function is a blocking wait.
seedJobRepository.scheduleBuild2(0).getFuture().get()
println '------------------------------------------------------------------------'

println '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
def seedJob = seedJobRepository.getItem('master')
// println seedJob.getClass()
// class org.jenkinsci.plugins.workflow.job.WorkflowJob
//
// println '~~~ Dumping seed job object....'
// println seedJob.dump()

// Execute and wait for actual `seed-job` build to be finished.
// The get() function is a blocking wait.
seedJob.scheduleBuild2(0).get()
println '------------------------------------------------------------------------'

println '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
// https://stackoverflow.com/a/50262834
for(job in Jenkins.instance.getAllItems(
    org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject)
) {
    println "Triggering scan of ${job.name} multibranch pipeline."

    // Option 1:
    // Execute and wait for `Scan Multibranch Pipeline Now` to be finished.
    // The getFuture().get() function is a blocking wait.
    // job.scheduleBuild2(0).getFuture().get()

    // Option 2:
    // Execute `Scan Multibranch Pipeline Now` asynchronously wihout waiting for it to finish.
    job.scheduleBuild2(0)
}
println '------------------------------------------------------------------------'
