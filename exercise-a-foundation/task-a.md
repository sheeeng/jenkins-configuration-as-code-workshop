# Exercise A - Foundation

We will be working with these files in this exercise.

- [jenkins/Dockerfile](jenkins/Dockerfile)
- [jenkins/jenkins.yaml](jenkins/jenkins.yaml)
- [jenkins/plugins.txt](jenkins/plugins.txt)

## Task: Use Jenkins Configuration as Code (JCasC) Plugin

It is important that we have included `configuration-as-code:1.35` in the configuration. Otherwise, Jenkins cannot process our Jenkins Configuration as Code's [jenkins/jenkins.yaml](jenkins/jenkins.yaml) file. Note that we have a predefined system message in [jenkins/jenkins.yaml](jenkins/jenkins.yaml) file.

### Task: Exclude Jenkins Configuration as Code (JCasC) Plugin

- The initial [jenkins/plugins.txt](jenkins/plugins.txt) file does not include that `configuration-as-code` line.

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

Was the configured system message, configured in [jenkins/jenkins.yaml](jenkins/jenkins.yaml) shown?

### Task: Include Jenkins Configuration as Code (JCasC) Plugin

- Query for the latest version of Jenkins Configuration as Code (JCasC) plugin.

```console
$ curl \
    --show-error \
    --silent \
    https://api.github.com/repos/jenkinsci/configuration-as-code-plugin/releases/latest \
    | jq --raw-output '.tag_name'
configuration-as-code-1.43
$
$ curl \
    --show-error \
    --silent \
    https://api.github.com/repos/jenkinsci/configuration-as-code-plugin/releases/latest \
    | grep "tag_name" \
    | cut --delimiter ":" --fields 2,3 \
    | tr --delete '\"' \
    | tr --delete ',' \
    | sed -e 's/^[[:space:]]*//'
configuration-as-code-1.43
```

- Run `make` again __with__ `configuration-as-code:1.43` being included in [jenkins/plugins.txt](jenkins/plugins.txt).

```patch
--- a/exercise-a-foundation/jenkins/plugins.txt
+++ b/exercise-a-foundation/jenkins/plugins.txt
@@ -4,7 +4,6 @@ bouncycastle-api:2.18
 build-timeout:1.20
 cloudbees-folder:6.14
 command-launcher:1.4
+configuration-as-code:1.43
 credentials-binding:1.23
 email-ext:2.77
 git:4.4.3
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

Was the configured system message, configured in [jenkins/jenkins.yaml](jenkins/jenkins.yaml) shown now?
