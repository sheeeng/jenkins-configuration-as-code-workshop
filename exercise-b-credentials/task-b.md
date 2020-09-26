# Exercise B - Credentials

We will be working with these files in this exercise.

- [docker-compose.yaml](docker-compose.yaml)
- [jenkins/Dockerfile](jenkins/Dockerfile)
- [jenkins/jenkins.yaml](jenkins/jenkins.yaml)
- [jenkins/secrets.env](jenkins/secrets.env)

The below section will explain how to configure the credentials options for Jenkins through Jenkins Configuration as Code (JCasC).

This workshop does not focus on managing secrets for production use. Use HashiCorp Vault, HashiCorp Consul, Google Cloud’s Secret Manager, AWS Secrets Manager, Azure Key Vault, and other Cloud Identity and Access Management options for managing secrets for production purposes.

## Task: Exposing Secrets

- Create a Freestyle job that run the below shell script.

```bash
echo
printenv JENKINS_ADMINISTRATOR_PASSWORD
echo
ls --all --numeric-uid-gid /run/secrets/JENKINS_SSH_PRIVATE_KEY
echo
cat --show-all /run/secrets/JENKINS_SSH_PRIVATE_KEY
```

Hints:

```text
+ echo

+ printenv JENKINS_ADMINISTRATOR_PASSWORD
Defender of the Universe!
+ echo

+ ls --all --numeric-uid-gid /run/secrets/JENKINS_SSH_PRIVATE_KEY
-rw------- 1 1000 1000 464 Aug  5 15:44 /run/secrets/JENKINS_SSH_PRIVATE_KEY
+ echo

+ cat --show-all /run/secrets/JENKINS_SSH_PRIVATE_KEY
-----BEGIN OPENSSH PRIVATE KEY-----$
AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA$
BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB$
CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC$
DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD$
EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE$
FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF$
-----END OPENSSH PRIVATE KEY-----$
```
