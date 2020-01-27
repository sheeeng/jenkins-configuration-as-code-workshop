FROM docker.io/jenkins/jenkins:2.249.1-jdk11

LABEL maintainer="Leonard Sheng Sheng Lee <leonard.sheng.sheng.lee@gmail.com>"

ARG JAVA_OPTS
ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false ${JAVA_OPTS:-}"

ENV JENKINS_HOME "/var/jenkins_home"

USER jenkins

COPY --chown=jenkins:jenkins startup_scripts/* /var/jenkins_home/init.groovy.d/

COPY scriptApproval.xml /usr/share/jenkins/ref/scriptApproval.xml

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN xargs /usr/local/bin/install-plugins.sh </usr/share/jenkins/ref/plugins.txt

COPY jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml
