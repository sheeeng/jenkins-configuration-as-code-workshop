FROM library/debian:10-slim

# Modified from https://github.com/jenkinsci/docker-ssh-slave project.

# https://github.com/hadolint/hadolint/wiki/DL4006#correct-code
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# Fix `debconf: delaying package configuration, since apt-utils is not installed`.
# https://github.com/tianon/docker-brew-ubuntu-core/issues/59#issuecomment-241154499
RUN export DEBIAN_FRONTEND=noninteractive \
    && apt-get update \
    && apt-get install \
        --assume-yes \
        --no-install-recommends \
        apt-utils\
        curl \
        git \
        gnupg2 \
        openssh-server \
        software-properties-common \
        sudo \
        wget \
    && apt-get autoremove \
    && rm --force --recursive /var/lib/apt/lists/*

# Fix `Warning: apt-key output should not be parsed (stdout is not a terminal)`.
# https://github.com/ansible/ansible/issues/28820#issuecomment-483106029
# https://salsa.debian.org/apt-team/apt/blob/master/cmdline/apt-key.in#L736
# RUN wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public \
#     | APT_KEY_DONT_WARN_ON_DANGEROUS_USAGE=1 apt-key add -
RUN curl \
    --fail \
    --location \
    --show-error \
    --silent \
    https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public \
    | APT_KEY_DONT_WARN_ON_DANGEROUS_USAGE=1 apt-key add -
RUN add-apt-repository \
    --yes \
    https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/

# Workaround
#
# Setting up adoptopenjdk-11-hotspot (11.0.4+11-2) ...
# update-alternatives: using /usr/lib/jvm/adoptopenjdk-11-hotspot-amd64/bin/jar to provide /usr/bin/jar (jar) in auto mode
# update-alternatives: error: error creating symbolic link '/usr/share/man/man1/jar.1.dpkg-tmp': No such file or directory
# dpkg: error processing package adoptopenjdk-11-hotspot (--configure):
#  installed adoptopenjdk-11-hotspot package post-installation script subprocess returned error exit status 2
# Errors were encountered while processing:
#  adoptopenjdk-11-hotspot
# E: Sub-process /usr/bin/dpkg returned an error code (1)
RUN mkdir --parents /usr/share/man/man1

RUN export DEBIAN_FRONTEND=noninteractive \
    && apt-get update \
    && apt-get install \
        --assume-yes \
        --no-install-recommends \
        adoptopenjdk-11-hotspot \
    && apt-get autoremove \
    && rm --force --recursive /var/lib/apt/lists/*

ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000
ARG JENKINS_AGENT_HOME=/home/${user}

ENV JAVA_HOME /usr/lib/jvm/adoptopenjdk-11-hotspot-amd64

ENV JENKINS_AGENT_HOME ${JENKINS_AGENT_HOME}

RUN groupadd -g ${gid} ${group} \
    && useradd \
        -d "${JENKINS_AGENT_HOME}" \
        -u "${uid}" \
        -g "${gid}" \
        -m -s /bin/bash "${user}"

RUN sed -i /etc/ssh/sshd_config \
        -e 's/#PermitRootLogin.*/PermitRootLogin no/' \
        -e 's/#RSAAuthentication.*/RSAAuthentication yes/'  \
        -e 's/#PasswordAuthentication.*/PasswordAuthentication no/' \
        -e 's/#SyslogFacility.*/SyslogFacility AUTH/' \
        -e 's/#LogLevel.*/LogLevel INFO/' && \
    mkdir /var/run/sshd

VOLUME "${JENKINS_AGENT_HOME}" "/tmp" "/run" "/var/run"
RUN mkdir --parents /opt/jenkins_workspace/ \
    && chown --recursive --quiet jenkins:jenkins /opt/jenkins_workspace/
WORKDIR /opt/jenkins_workspace/

RUN mkdir --parents /var/jenkins_home/.m2/
COPY settings.xml /var/jenkins_home/.m2/settings.xml

COPY setup-sshd /usr/local/bin/setup-sshd

EXPOSE 22

ENTRYPOINT ["setup-sshd"]
