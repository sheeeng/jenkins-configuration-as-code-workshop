# Exercise C - Seed Job

We will be working with these files in this exercise.

- [docker-compose.yaml](docker-compose.yaml)
- [jenkins/Dockerfile](jenkins/Dockerfile)
- [jenkins/jenkins.yaml](jenkins/jenkins.yaml)
- [jenkins/scriptApproval.xml](jenkins/scriptApproval.xml)
- [jenkins/secrets.env](jenkins/secrets.env)

## Task: Configure GitHub Access

- Modify the `secrets.env` file inside `jenkins` directory with your credentials.

```text
GITHUB_SSH_PRIVATE_KEY_USERNAME=changeit
GITHUB_SSH_PRIVATE_KEY_PASSWORD=changeit
```

- Add GitHub credential to [jenkins/jenkins.yaml](jenkins/jenkins.yaml).

```patch
--- a/exercise-c-seed-job/jenkins/jenkins.yaml
+++ b/exercise-c-seed-job/jenkins/jenkins.yaml
@@ -32,6 +32,19 @@ jenkins:
       groupIdStrategy: "caseInsensitive"
       userIdStrategy: "caseInsensitive"

+credentials:
+  system:
+    domainCredentials:
+    - credentials:
+      - basicSSHUserPrivateKey:
+          scope: GLOBAL
+          passphrase: ${JENKINS_SSH_PRIVATE_KEY_PASSWORD}
+          id: "GITHUB_SSH_KEY"
+          description: "This SSH private key is needed to establish connection to GitHub."
+          privateKeySource:
+            directEntry:
+              privateKey: ${JENKINS_SSH_PRIVATE_KEY}
+
 unclassified:
   location:
     adminAddress: "Leonard Sheng Sheng Lee <leonard.sheng.sheng.lee@gmail.com>"
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.
