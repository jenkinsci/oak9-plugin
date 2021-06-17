oak9.ci.jenkins

## Introduction

This plugin allows for a quick and easy integration between Jenkins and oak9 for scanning your infrastructure as code 
files for security considerations as part of your build pipeline. 

## Getting started (Freestyle Project)

After installing the plugin, take the following steps to configure the oak9 Runner as part of your build process.

1. Provide your oak9 API key as a Credential. The credential type must be `Secret Text` and your API key must be placed
   in the `Secret` field.
1. Ensure your code is present in the Jenkins workspace for your Freestyle Project. The plugin makes no assumptions about
   where the code comes from, so you may be pull it from an SCM system, copy it as an artifact from another Jenkins
   project, etc.
1. Create a new Free Style Project
1. Under `Build` use the `Add Build Step` dropdown to select `oak9 Runner`
1. Provide your oak9 org ID and project ID, select your previously created credential, and choose a severity at which
point the Jenkins job should fail.

## Getting started (Pipeline Project)

1. Provide your oak9 API key as a Credential. The credential type must be `Secret Text` and your API key must be placed
   in the `Secret` field.

### Building for Dev
This project uses Maven for fetching dependencies, building code, and running a test environment of Jenkins. 

To build your code and run tests:
`mvn install`

To build your code and run a Jenkins instance:
`mvn hpi:run`


