#!/usr/bin/env bash

set -ex

command -v aws >/dev/null 2>&1 || { pip install --user awscli; export PATH=$PATH:$HOME/.local/bin; }

ARTIFACT=cfn-multi-vpc
PROJECT_VERSION=$(./gradlew --quiet projectVersion)

publish_site() {
    aws s3 sync build/asciidoc/html5/ "s3://cfn-stacks.com/docs/${ARTIFACT}/${1}"
    aws s3 cp build/asciidoc/pdf/index.pdf "s3://cfn-stacks.com/docs/${ARTIFACT}/${1}/${ARTIFACT}.pdf"
}

# Publish the site for the current version
publish_site $PROJECT_VERSION

# If this is a release (not a snapshot) then publish as latest as well
if [[ $PROJECT_VERSION != *"SNAPSHOT"* ]]; then
    publish_site "latest"
fi
