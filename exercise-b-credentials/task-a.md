# Exercise B - Credentials

We will be working with these files in this exercise.

- [docker-compose.yaml](docker-compose.yaml)
- [jenkins/Dockerfile](jenkins/Dockerfile)
- [jenkins/jenkins.yaml](jenkins/jenkins.yaml)
- [jenkins/secrets.env](jenkins/secrets.env)

The below section will explain how to configure the credentials options for Jenkins through Jenkins Configuration as Code (JCasC).

This workshop does not focus on managing secrets for production use. Use HashiCorp Vault, HashiCorp Consul, Google Cloud’s Secret Manager, AWS Secrets Manager, Azure Key Vault, and other Cloud Identity and Access Management options for managing secrets for production purposes.

## Task: Handling Secrets

- Create a `secrets.env` file inside `jenkins` directory with below content.

```text
JENKINS_SSH_PRIVATE_KEY_PASSWORD=
JENKINS_ADMINISTRATOR_USERNAME=voltron
JENKINS_ADMINISTRATOR_PASSWORD=defender of the universe
LDAP_SERVICE_USERNAME=
LDAP_SERVICE_PASSWORD=changeit
NEXUS_USERNAME=admin
NEXUS_USERPASSWORD=admin123
```

- Modify the [docker-compose.yaml](docker-compose.yaml) file with the following example.

These changes will mount our SSH private key and the `secrets.env` file.

```patch
--- a/exercise-b-credentials/docker-compose.yaml
+++ b/exercise-b-credentials/docker-compose.yaml
@@ -11,6 +11,13 @@ services:
       - TZ=Europe/Oslo
     volumes:
       - jenkins_home:/var/jenkins_home
+    secrets:
+      - JENKINS_SSH_PRIVATE_KEY
+    env_file: ./jenkins/secrets.env
+
+secrets:
+  JENKINS_SSH_PRIVATE_KEY:
+    file: ~/.ssh/id_rsa

 volumes:
   jenkins_home:
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.
