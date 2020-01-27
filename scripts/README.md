# README

See [CLI](https://jenkins.io/doc/book/managing/cli/#remoting-connection-mode) section.

## Getting Started

The CLI client can be downloaded directly from a Jenkins master at the URL `/jnlpJars/jenkins-cli.jar`, in effect `${JENKINS_URL}/jnlpJars/jenkins-cli.jar`.

While a CLI `.jar` can be used against different versions of Jenkins, should any compatibility issues arise during use, please re-download the latest `.jar` file from the Jenkins master.

```bash
java \
    -jar ${HOME}/Downloads/jenkins-cli.jar \
    -noCertificateCheck \
    -s http://127.0.0.1:8080 \
    -auth ${JENKINS_USER_ID}:${JENKINS_API_TOKEN} \
    groovy = < scripts/listJobs.groovy
```

```bash
make compose-exec-jenkins-cli \
    CMD="groovy = < scripts/listPlugins.groovy"
```
