pipeline {
    agent any
    options {
        // Keep the 10 most recent builds
        buildDiscarder(logRotator(numToKeepStr:'10'))
    }
    stages {
        stage("Stage Checkout") {
            steps {
                checkout scm
                sh 'git submodule update --init'
            }
        }

        stage("Stage Changelog") {
            steps {
                gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                //def releaseNotes = "${env.GIT_BRANCH}\n\n${env.GIT_CHANGELOG}"
                echo "Release Notes: ${gitCommit}"
            }
        }

        stage('Stage Build') {
            steps {
                //branch name from Jenkins environment variables
                echo "My branch is: ${env.BRANCH_NAME}"

                //build your gradle flavor, passes the current build number as a parameter to gradle
                sh "./gradlew clean assembleDebug -PBUILD_NUMBER=${env.BUILD_NUMBER}"
            }
        }

        stage('Stage Archive') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/*.apk', fingerprint: true
            }
        }

        stage('Stage Upload To Fabric') {
            steps {
                //env.ORG_GRADLE_PROJECT_BETA_RELEASE_NOTES=releaseNotes
                sh "./gradlew crashlyticsUploadDistributionDebug -PBUILD_NUMBER=${env.BUILD_NUMBER}"
            }
        }
    }
}

def getChangelog() {
    def changeLogSets = currentBuild.rawBuild.changeSets
    def changelog = ""
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            changelog += entry.msg + '\n'
        }
    }
    return changelog
}
