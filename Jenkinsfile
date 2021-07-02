#!/usr/bin/env groovy

/* `buildPlugin` step provided by: https://github.com/jenkins-infra/pipeline-library */
buildPlugin(configurations: [
  [ platform: "linux", jdk: "11" ],
  [ platform: "windows", jdk: "11" ],
  [ platform: "linux", jdk: "11", jenkins: "2.222.4", javaLabel: "8" ],
  [ platform: "windows", jdk: "11", jenkins: "2.222.4", javaLabel: "8" ]
])