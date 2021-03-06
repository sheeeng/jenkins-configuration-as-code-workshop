## Task: Change Nexus Administrator Password

- Run `make`. See [Makefile](Makefile) for more information.

- Get the uniquely generated administrator password of Nexus.

```bash
docker exec -it \
  $(docker ps --filter "name=nexus" --quiet)  \
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

Enable anonymous access means that by default, users can search, browse and download components from repositories without credentials. Please consider the security implications for your organization.

Disable anonymous access should be chosen with care, as it will require credentials for all users and/or build tools.

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
