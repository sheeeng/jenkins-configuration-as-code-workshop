# Exercise D - Shared Libraries

We will be working with these files in this exercise.

- [docker-compose.yaml](docker-compose.yaml)
- [jenkins/jenkins.yaml](jenkins/jenkins.yaml)
- [jenkins/secrets.env](jenkins/secrets.env)

## Task: Configure Jenkins Shared Pipeline Library

- Modify [jenkins/jenkins.yaml](jenkins/jenkins.yaml) to configure global libraries.

```patch
--- a/exercise-d-shared-libraries/jenkins/jenkins.yaml
+++ b/exercise-d-shared-libraries/jenkins/jenkins.yaml
@@ -51,6 +51,18 @@ unclassified:
   location:
     adminAddress: "Leonard Sheng Sheng Lee <leonard.sheng.sheng.lee@gmail.com>"
     url: "http://localhost:8080"
+  globalLibraries:
+    libraries:
+      - name: "shared-pipeline-library"
+        allowVersionOverride: true
+        defaultVersion: "master"
+        includeInChangesets: true
+        retriever:
+          modernSCM:
+            scm:
+              git:
+                remote: "git@github.com:sheeeng/jenkins-configuration-as-code-shared-pipeline-library.git"
+                credentialsId: "GITHUB_SSH_KEY"

 jobs:
   - script: |
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.
