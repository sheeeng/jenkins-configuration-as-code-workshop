# Exercises

We will be working with these files in this exercise.

- [exercise-d-shared-libraries/docker-compose.yaml](exercise-d-shared-libraries/docker-compose.yaml)
- [exercise-d-shared-libraries/jenkins/jenkins.yaml](exercise-d-shared-libraries/jenkins/jenkins.yaml)
- [exercise-d-shared-libraries/jenkins/secrets.env](exercise-d-shared-libraries/jenkins/secrets.env)

## Task: Configure Nexus

## Task: Configure Nexus Credentials for Jenkins

- Verify the `secrets.env` file inside `jenkins` directory contains Nexus credentials.

```text
NEXUS_USERNAME=admin
NEXUS_USERPASSWORD=admin123
```

### Task: Add Jenkins Agent to Docker Compose

- Modify [docker-compose.yaml](docker-compose.yaml) to configure a build agent.

- Include your own SSH public key.

```patch
--- a/exercise-d-shared-libraries/docker-compose.yaml
+++ b/exercise-d-shared-libraries/docker-compose.yaml
@@ -12,12 +12,23 @@ services:
       - CASC_JENKINS_CONFIG=/usr/share/jenkins/ref/jenkins.yaml
     volumes:
       - jenkins_home:/var/jenkins_home
+      - ${HOME}/.ssh:/var/jenkins_home/.ssh:rw
+      - ${HOME}/.m2:/var/jenkins_home/.m2:rw
     secrets:
       - JENKINS_SSH_PRIVATE_KEY
     env_file: ./jenkins/secrets.env
     depends_on:
+      - agent-java
       - slapd

+  agent-java:
+    hostname: "agent-java"
+    build: agent-java
+    ports:
+      - 22:22
+    environment:
+      - JENKINS_BUILD_AGENT_SSH_PUBLIC_KEY=ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQC4a5IBmV1jdssc8nrCWcFwpK++c1wicaY9aVY3VXnRSQvl5TmuluXg44ARWjGNVe9nRiprj5sUe0FnGJkk4Dglc1+ZC6i69dGt+NneGGtgt63+hwJZrQ3AtgK33YQLrSsfaEHqVtON8TTyAt5sN94Jq7tP24pdKhwGl7/HZFsRR4tGrQQPGuxAbhHZpVClAZ6cf0woky66cQa9iQSNO6apC4wH6kP8RFOcTI0aJNysKwf5ioXNXPiKvKhAZKpqd269wIWThfvJv9Qk9dU2W1EtIQGwZn/EfzxtJcGe/PnHmN8jiTPzMGCjX9JeKMTphUpz5BSGK+XzWcg1vcd/BK/NwxMNLPS91AgGw65qi2WGcV34vcWVH0uRE8smCS2FeKjBxD/iLYcwEBkH7SNcnvU4pII7JsE8ueJV6CHTf9sT/orYgU4CHgIDdxQiVZSWthNBAVxLOZVedEumRdolDaTQMvbMnDhW5r1GLwsIAtFDKCtpry0r+10FCqW332SKE6HZ++QUWQn6gN7BDfV+4X8ZTpQCw3dyeRaoYq1lVeNke2dwXgus+Vl8H86AeXkr43TeRGYkvW08shrvn9mUw3rPKHWi/fKcJFEYvSePcEan5AtnVPZrPZhZV04Xu7Jj6MVrkklhVkNw3kvjLo0iO1pfDX07vKuA1rNPHbMFigIIJw== leonard.sheng.sheng.lee@gmail.com
+
   slapd:
     build:
       context: ./slapd
@@ -35,9 +46,18 @@ services:
   #   depends_on:
   #     - slapd

+  nexus:
+    build:
+      context: ./nexus
+    ports:
+      - 8081:8081
+    volumes:
+      - nexus_data:/nexus-data
+
 secrets:
   JENKINS_SSH_PRIVATE_KEY:
     file: ~/.ssh/id_rsa

 volumes:
   jenkins_home:
+  nexus_data:
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

## Task: Change Nexus Administrator Password

- Run `make`. See [Makefile](Makefile) for more information.

- Get the uniquely generated administrator password of Nexus.

```bash
docker exec -it \
  $(docker ps --filter "name=exercise-d-shared-libraries_nexus_1" --quiet)  \
  bash -c "cat /nexus-data/admin.password && echo"
```

Note: Nexus will ask you to change the password upon first log in. We use `admin123` as password for this workshop.

```text
Setup
1 of 4

This wizard will help you complete required setup tasks.

Please choose a password for the admin user
```

```text
Please choose a password for the admin user
2 of 4

New password: admin123
Confirm password: admin123
```

```text
Configure Anonymous Access
3 of 4

Enabling anonymous access will allow unauthenticated downloads, browsing, and searching of repository content by default. Permissions for unauthenticated users can be changed by editing the roles assigned to the anonymous user.

More information
[ ] Enable anonymous access
[x] Disable anonymous access
```

```text
Complete
4 of 4

The setup tasks have been completed, enjoy using Nexus Repository Manager!
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8081](http://localhost:8081) to access Nexus.
