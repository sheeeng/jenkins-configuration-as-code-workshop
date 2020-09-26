# Exercise C - Seed Job

We will be working with these files in this exercise.

- [docker-compose.yaml](docker-compose.yaml)
- [jenkins/Dockerfile](jenkins/Dockerfile)
- [jenkins/jenkins.yaml](jenkins/jenkins.yaml)
- [jenkins/scriptApproval.xml](jenkins/scriptApproval.xml)
- [jenkins/secrets.env](jenkins/secrets.env)

## Task: Add Seed Job

- Modify [jenkins/jenkins.yaml](jenkins/jenkins.yaml) to use seed job.

```patch
--- a/exercise-c-seed-job/jenkins/jenkins.yaml
+++ b/exercise-c-seed-job/jenkins/jenkins.yaml
@@ -36,3 +36,35 @@ unclassified:
   location:
     adminAddress: "Leonard Sheng Sheng Lee <leonard.sheng.sheng.lee@gmail.com>"
     url: "https://localhost:8080"
+
+jobs:
+  - script: |
+      folder('germ') {
+        multibranchPipelineJob('germ/seed-job') {
+          description(
+            '\nInitial seed job to populate other predefined jobs for Jenkins.'
+          )
+          configure {
+            it / sources / 'data' / 'jenkins.branch.BranchSource' << {
+              source(class: 'jenkins.plugins.git.GitSCMSource') {
+                credentialsId('GITHUB_SSH_KEY')
+                remote('git@github.com:sheeeng/jenkins-configuration-as-code-seed-job.git')
+                traits {
+                    'jenkins.plugins.git.traits.BranchDiscoveryTrait'()
+                    'jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait' {
+                      includes('master')
+                      excludes('') // Needs to be set for trait to work
+                    }
+                }
+              }
+              strategy(class: 'jenkins.branch.DefaultBranchPropertyStrategy') {
+                properties(class: 'java.util.Arrays\$ArrayList') {
+                  a(class: 'jenkins.branch.BranchProperty-array') {
+                    'jenkins.branch.NoTriggerBranchProperty'()
+                  }
+                }
+              }
+            }
+          }
+        }
+      }
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

- Log in using `superuser` account from LDAP directory on the Jenkins [web user interface](http://localhost:8080/).

- The generated [seed-job](http://localhost:8080/job/germ/job/seed-job/) should be available now.
