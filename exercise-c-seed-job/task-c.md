# Exercises

We will be working with these files in this exercise.

- [exercise-c-seed-job/docker-compose.yaml](exercise-c-seed-job/docker-compose.yaml)
- [exercise-c-seed-job/jenkins/Dockerfile](exercise-c-credentials/jenkins/Dockerfile)
- [exercise-c-seed-job/jenkins/jenkins.yaml](exercise-c-seed-job/jenkins/jenkins.yaml)
- [exercise-c-seed-job/jenkins/scriptApproval.xml](exercise-c-seed-job/jenkins/scriptApproval.xml)
- [exercise-c-seed-job/jenkins/secrets.env](exercise-c-seed-job/jenkins/secrets.env)

## Task: Define Executors and Labels

- Modify [exercise-c-seed-job/jenkins/jenkins.yaml](exercise-c-seed-job/jenkins/jenkins.yaml) to configure number of executors and node label(s).

```patch
--- a/exercise-c-seed-job/jenkins/jenkins.yaml
+++ b/exercise-c-seed-job/jenkins/jenkins.yaml
@@ -8,6 +8,8 @@ jenkins:
     Welcome to Jenkins!    ٩(◕‿◕)۶
     This Jenkins was configured with Jenkins Configuration as Code plugin.
     You can freely modify its configuration without repercussion.
+  numExecutors: 2
+  labelString: "jenkins-label"
   authorizationStrategy:
     globalMatrix:
       grantedPermissions:
```

If we did not have do not include the above configuration, we are stuck with below log.

```console
$ make compose-logs
...
...
...
jenkins_1  | 2019-08-25T16:09:59.861010300Z Aug 25, 2019 6:09:59 PM org.kohsuke.stapler.LocaleDrivenResourceProvider getLocaleDrivenResourceProviders
jenkins_1  | 2019-08-25T16:09:59.861151000Z INFO: Registered LocaleDrivenResourceProvider: jenkins.MetaLocaleDrivenResourceProvider@599a988a
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.
