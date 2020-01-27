# Exercises

We will be working with these files in this exercise.

- [exercise-a-foundation/jenkins/Dockerfile](exercise-a-foundation/jenkins/Dockerfile)
- [exercise-a-foundation/jenkins/jenkins.yaml](exercise-a-foundation/jenkins/jenkins.yaml)
- [exercise-a-foundation/jenkins/plugins.txt](exercise-a-foundation/jenkins/plugins.txt)

## Task: Review Administrative Monitors (See Upper Right Square Numeric Red Box)

We notice that there are few active administrative monitors.

They are shown when we click on the red box on the upper right corner.

How could we fix some of them by Jenkins Configuration as Code (JCasC)?

### Task: Fix Jenkins Root URL

```text
Jenkins root URL is empty but is required for the proper operation of many Jenkins features like email notifications, PR status update, and environment variables such as BUILD_URL.

Please provide an accurate value in Jenkins configuration.
```

```patch
--- a/exercise-a-foundation/jenkins/jenkins.yaml
+++ b/exercise-a-foundation/jenkins/jenkins.yaml
@@ -8,3 +8,8 @@ jenkins:
     Welcome to Jenkins!    ٩(◕‿◕)۶
     This Jenkins was configured with Jenkins Configuration as Code plugin.
     You can freely modify its configuration without repercussion.
+
+unclassified:
+  location:
+    adminAddress: "Leonard Sheng Sheng Lee <leonard.sheng.sheng.lee@gmail.com>"
+    url: "http://127.0.0.1:8080"
```

Hints:

You can configure system manually first to explore how the configuration is being used.

Go to `Jenkins` > `Manage Jenkins` > [Configure System](http://localhost:8080/configure).

Enter `http://localhost:8080` for `Jenkins URL` input field under `Jenkins Location`.

Enter your email for `System Admin e-mail address` input field under `Jenkins Location`.

Go to `Jenkins` > `Manage Jenkins` > [Configuration as Code](http://localhost:8080/configuration-as-code/).

Click `View Configuration` to verify the applied configuration.

### Task: Set Up Security

```text
Jenkins is currently unsecured and allows anyone on the network to launch processes on your behalf. It is recommended to set up security and to limit anonymous access even on private networks.
```

```patch
--- a/exercise-a-foundation/jenkins/jenkins.yaml
+++ b/exercise-a-foundation/jenkins/jenkins.yaml
@@ -8,3 +8,11 @@ jenkins:
     Welcome to Jenkins!    ٩(◕‿◕)۶
     This Jenkins was configured with Jenkins Configuration as Code plugin.
     You can freely modify its configuration without repercussion.
+  authorizationStrategy: "loggedInUsersCanDoAnything"
+  securityRealm:
+    local:
+      allowsSignup: false
+      enableCaptcha: false
+      users:
+        - id: ${JENKINS_ADMINISTRATOR_USERNAME:-administrator}
+          password: ${JENKINS_ADMINISTRATOR_PASSWORD:-changeit}
```

Hints:

You can set up security manually first to explore how the configuration is being used.

Go to `Jenkins` > `Manage Jenkins` > [Configure Global Security](http://localhost:8080/configureSecurity/).

Enable `Logged-in users can do anything` and `Allow anonymous read access` under `Authorization`.

Go to `Jenkins` > `Manage Jenkins` > [Configuration as Code](http://localhost:8080/configuration-as-code/).

Click `View Configuration` to verify the applied configuration.

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.
