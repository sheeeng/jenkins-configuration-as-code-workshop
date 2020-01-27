#!/usr/bin/env python

# Workaround `host key verification failed` error.
# https://serverfault.com/a/701637

# stderr: Host key verification failed.
# fatal: Could not read from remote repository.
#
# Please make sure you have the correct access rights
# and the repository exists.

import subprocess, sys, shutil
from subprocess import DEVNULL, PIPE
result = subprocess.run(['ssh-keyscan', 'github.com'], stdout=PIPE, stderr=DEVNULL, universal_newlines=True, check=True)
# print(result.returncode, result.stdout, result.stderr)

with open('known_hosts', 'w') as w:
    w.write(result.stdout)

result = subprocess.run(['ssh-keygen', '-lf', 'known_hosts'], stdout=PIPE, stderr=PIPE, universal_newlines=True, check=True)
# print(result.returncode, result.stdout, result.stderr)

github_ssh_public_key_signature = result.stdout.split()

from pathlib import Path
home = str(Path.home())

if github_ssh_public_key_signature[0] == '2048' \
    and github_ssh_public_key_signature[1] == 'SHA256:nThbg6kXUpJWGl7E1IGOCspRomTxdCARLviKw6E5SY8' \
    and github_ssh_public_key_signature[2] == 'github.com' \
    and github_ssh_public_key_signature[3] == '(RSA)':
    print('Host key verification for GitHub succeeded.')
    shutil.move('known_hosts', home+"/.ssh/known_hosts_github")
else:
    print('''
        Host key verification for GitHub failed.
        You need to manually verify with GitHub's public key fingerprints.
        See https://help.github.com/en/articles/githubs-ssh-key-fingerprints page.
    ''')
