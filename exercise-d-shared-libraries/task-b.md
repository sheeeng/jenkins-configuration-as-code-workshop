# Exercises

We will be working with these files in this exercise.

- [exercise-d-shared-libraries/docker-compose.yaml](exercise-d-shared-libraries/docker-compose.yaml)
- [exercise-d-shared-libraries/jenkins/jenkins.yaml](exercise-d-shared-libraries/jenkins/jenkins.yaml)
- [exercise-d-shared-libraries/jenkins/secrets.env](exercise-d-shared-libraries/jenkins/secrets.env)

## Task: Configure Jenkins Build Agents

- Modify [jenkins/jenkins.yaml](jenkins/jenkins.yaml) to configure a build agent, tools, and Nexus credentials in Jenkins.

```patch
--- a/exercise-d-shared-libraries/jenkins/jenkins.yaml
+++ b/exercise-d-shared-libraries/jenkins/jenkins.yaml
@@ -33,6 +33,41 @@ jenkins:
       disableRolePrefixing: true
       groupIdStrategy: "caseInsensitive"
       userIdStrategy: "caseInsensitive"
+  remotingSecurity:
+    enabled: true
+  agentProtocols:
+  - "JNLP4-connect"
+  - "Ping"
+  slaveAgentPort: 50000
+  nodes:
+  - permanent:
+      name: "agent-java"
+      labelString: "java-label"
+      numExecutors: 2
+      remoteFS: "/opt/jenkins_workspace/"
+      launcher:
+        ssh:
+          credentialsId: "GITHUB_SSH_KEY"
+          host: "agent-java"
+          port: 22
+          sshHostKeyVerificationStrategy:
+            manuallyTrustedKeyVerificationStrategy:
+              requireInitialManualTrust: false
+      retentionStrategy: "always"
+
+tool:
+  git:
+    installations:
+      - name: Default
+        home: "git"
+  maven:
+    installations:
+    - name: "maven-3"
+      properties:
+      - installSource:
+          installers:
+          - maven:
+              id: "3.6.1"

 credentials:
   system:
@@ -46,6 +81,11 @@ credentials:
           privateKeySource:
             directEntry:
               privateKey: ${JENKINS_SSH_PRIVATE_KEY}
+      - usernamePassword:
+          id: "NEXUS_USER"
+          password: ${NEXUS_USERPASSWORD}
+          scope: GLOBAL
+          username: ${NEXUS_USERNAME}

 unclassified:
   location:
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.
