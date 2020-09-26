# Exercise C - Seed Job

We will be working with these files in this exercise.

- [docker-compose.yaml](docker-compose.yaml)
- [jenkins/Dockerfile](jenkins/Dockerfile)
- [jenkins/jenkins.yaml](jenkins/jenkins.yaml)
- [jenkins/scriptApproval.xml](jenkins/scriptApproval.xml)
- [jenkins/secrets.env](jenkins/secrets.env)

## Task: Include Initialization Scripts

- Modify [jenkins/Dockerfile](jenkins/Dockerfile) to include the initialization scripts.

```patch
--- a/exercise-c-seed-job/jenkins/Dockerfile
+++ b/exercise-c-seed-job/jenkins/Dockerfile
@@ -9,6 +9,8 @@ ENV JENKINS_HOME "/var/jenkins_home"

 USER jenkins

+COPY --chown=jenkins:jenkins startup_scripts/* /var/jenkins_home/init.groovy.d/
+
 COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
 RUN xargs /usr/local/bin/install-plugins.sh </usr/share/jenkins/ref/plugins.txt

```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

- Log in using `superuser` account from LDAP directory on the Jenkins [web user interface](http://localhost:8080/).

- Navigate to the generated [seed-job](http://localhost:8080/job/germ/job/seed-job/).

- ~~Click [Scan Multibranch Pipeline Now](http://localhost:8080/job/germ/job/seed-job/build?delay=0).~~

  This step is automatically executed by [jenkins/startup_scripts/runJobs.groovy](jenkins/startup_scripts/runJobs.groovy).

- We see that the first [seed-job](http://localhost:8080/job/germ/job/seed-job/job/master/1/console) had failed to execute with below error.

```text
ERROR: script not yet approved for use
Finished: FAILURE
```

## Task: Approve Initialization Scripts

- Go to [In-process Script Approval [Jenkins]](http://localhost:8080/scriptApproval/) to approve the seed-job script.

- Docker execute into the Jenkins container to obtain the approved hashes.

```bash
docker exec \
     --interactive \
     --tty \
     --workdir /var/jenkins_home \
     $(docker ps --filter name="jenkins_1" --quiet) \
     bash -c "cat scriptApproval.xml && echo"
```

- Create or modify [jenkins/scriptApproval.xml](jenkins/scriptApproval.xml) with the output from above command.

```xml
<?xml version='1.1' encoding='UTF-8'?>
<scriptApproval plugin="script-security@1.62">
  <approvedScriptHashes>
    <string>b0534d7b143a855c396dcce64e71cf9106595773</string>
  </approvedScriptHashes>
  <approvedSignatures/>
  <aclApprovedSignatures/>
  <approvedClasspathEntries/>
  <pendingScripts/>
  <pendingSignatures/>
  <pendingClasspathEntries/>
</scriptApproval>
```

- Include the [jenkins/scriptApproval.xml](jenkins/scriptApproval.xml) in [jenkins/Dockerfile](jenkins/Dockerfile).

```patch
--- a/exercise-c-seed-job/jenkins/Dockerfile
+++ b/exercise-c-seed-job/jenkins/Dockerfile
@@ -9,6 +9,8 @@ ENV JENKINS_HOME "/var/jenkins_home"

 USER jenkins

+COPY scriptApproval.xml /usr/share/jenkins/ref/scriptApproval.xml
+
 COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
 RUN xargs /usr/local/bin/install-plugins.sh </usr/share/jenkins/ref/plugins.txt

```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

- Log in using `superuser` account from LDAP directory.

- We expect to see that [seed-job](http://localhost:8080/job/germ/job/seed-job/job/master/) has build successfully.
