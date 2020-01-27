# Exercises

We will be working with these files in this exercise.

- [exercise-b-credentials/docker-compose.yaml](exercise-b-credentials/docker-compose.yaml)
- [exercise-b-credentials/jenkins/Dockerfile](exercise-a-credentials/jenkins/Dockerfile)
- [exercise-b-credentials/jenkins/jenkins.yaml](exercise-b-credentials/jenkins/jenkins.yaml)
- [exercise-b-credentials/jenkins/secrets.env](exercise-b-credentials/jenkins/secrets.env)

The below section will explain how to configure the credentials options for Jenkins through Jenkins Configuration as Code (JCasC).

This workshop does not focus on managing secrets for production use. Use HashiCorp Vault, HashiCorp Consul, Google Cloud’s Secret Manager, AWS Secrets Manager, Azure Key Vault, and other Cloud Identity and Access Management options for managing secrets for production purposes.

Multiple Security Realms are not supported, when we last checked in September 2020. See [JENKINS-15063](https://issues.jenkins-ci.org/browse/JENKINS-15063).

## Task: Use Lightweight Directory Access Protocol (LDAP)

- Modify [exercise-b-credentials/docker-compose.yaml](exercise-b-credentials/docker-compose.yaml) to use LDAP directory.

```patch
--- a/exercise-b-credentials/docker-compose.yaml
+++ b/exercise-b-credentials/docker-compose.yaml
@@ -11,6 +11,25 @@ services:
       - TZ=Europe/Oslo
     volumes:
       - jenkins_home:/var/jenkins_home
+    depends_on:
+      - slapd
+
+  slapd:
+    build:
+      context: ./slapd
+    ports:
+      - 389:389
+      - 636:636
+
+  phpldapadmin:
+    image: osixia/phpldapadmin:0.8.0
+    environment:
+      PHPLDAPADMIN_LDAP_HOSTS: slapd
+      PHPLDAPADMIN_HTTPS: 'false'
+    ports:
+      - 8090:80
+    depends_on:
+      - slapd

 volumes:
   jenkins_home:
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

- Log in to [phpLDAPadmin](http://localhost:8090/).

- Enter `cn=admin,dc=acme,dc=local` for `Login DN:` field.

- Enter `changeit` for `Password:` field.

- Examine the Lightweight Directory Access Protocol (LDAP) structure.

```text
+--> dc=acme,dc=local (3)
---> cn=admin
+--> ou=Groups (4)
| ---> cn=Acme Managers
| ---> cn=Acme Servicers
| ---> cn=Acme Superusers
| ---> cn=Acme Users
| ---> Create new entry here
+--> ou=Users (4)
| ---> cn=manager
| ---> cn=service
| ---> cn=superuser
| ---> cn=user
| ---> Create new entry here
---> Create new entry here
```

- Modify [exercise-b-credentials/jenkins/jenkins.yaml](exercise-b-credentials/jenkins/jenkins.yaml) to use LDAP directory.

```patch
--- a/exercise-b-credentials/jenkins/jenkins.yaml
+++ b/exercise-b-credentials/jenkins/jenkins.yaml
@@ -10,12 +10,19 @@ jenkins:
     You can freely modify its configuration without repercussion.
   authorizationStrategy: "loggedInUsersCanDoAnything"
   securityRealm:
-    local:
-      allowsSignup: false
-      enableCaptcha: false
-      users:
-        - id: ${JENKINS_ADMINISTRATOR_USERNAME:-administrator}
-          password: ${JENKINS_ADMINISTRATOR_PASSWORD:-changeit}
+    ldap:
+      configurations:
+      - groupSearchFilter: "(& (cn={0}) (objectclass=posixGroup) )"
+        inhibitInferRootDN: false
+        managerDN: "cn=service,ou=Users,dc=acme,dc=local"
+        managerPasswordSecret: ${LDAP_SERVICE_PASSWORD}
+        rootDN: "dc=acme,dc=local"
+        server: "ldap://slapd:389"
+      disableMailAddressResolver: false
+      disableRolePrefixing: true
+      groupIdStrategy: "caseInsensitive"
+      userIdStrategy: "caseInsensitive"

 unclassified:
   location:
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

- Log in using `superuser`, `service`, `manager`, or `user` as username and using `changeit` as password.

Can all of them can access [Manage Jenkins](http://localhost:8080/manage) page?

## Task: Restrict Lightweight Directory Access Protocol (LDAP) Access

We want to limit access to [Manage Jenkins](http://localhost:8080/manage) page depending on the LDAP groups.

Also, we want to allow folks to view Jenkins only after being authenticated.

- Modify [jenkins/jenkins.yaml](jenkins/jenkins.yaml) file.

```patch
--- a/exercise-b-credentials/jenkins/jenkins.yaml
+++ b/exercise-b-credentials/jenkins/jenkins.yaml
@@ -8,7 +8,15 @@ jenkins:
     Welcome to Jenkins!    ٩(◕‿◕)۶
     This Jenkins was configured with Jenkins Configuration as Code plugin.
     You can freely modify its configuration without repercussion.
-  authorizationStrategy: "loggedInUsersCanDoAnything"
+  authorizationStrategy:
+    globalMatrix:
+      grantedPermissions:
+        - "Job/Build:authenticated"
+        - "Job/Cancel:authenticated"
+        - "Job/Read:authenticated"
+        - "Overall/Administer:Acme Superusers"
+        - "Overall/Read:authenticated"
+        - "View/Read:authenticated"
   securityRealm:
     local:
       allowsSignup: false
```

- Run `make`. See [Makefile](Makefile) for more information.

- Open [http://localhost:8080](http://localhost:8080) to access Jenkins.

- Log in using `service`, `manager`, or `user` with `changeit` password.

Can they can access [Manage Jenkins](http://localhost:8080/manage) page now?

Is it only `superuser` user that can access [Manage Jenkins](http://localhost:8080/manage) page now?
