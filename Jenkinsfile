node {
  // Mark the code checkout 'stage'....
  stage 'Stage Checkout'

  // Checkout code from repository and update any submodules
  checkout scm
  sh 'git submodule update --init'

  stage 'Stage Build'

  //branch name from Jenkins environment variables
  echo "My branch is: ${env.BRANCH_NAME}"

  //build your gradle flavor, passes the current build number as a parameter to gradle
  sh "./gradlew clean assembleDebug -PBUILD_NUMBER=${env.BUILD_NUMBER}"

  stage 'Stage Archive'
  //tell Jenkins to archive the apks
  archiveArtifacts artifacts: 'app/build/outputs/apk/*.apk', fingerprint: true

  env.GIT_CHANGELOG = getChangelog()
  def releaseNotes = "${env.GIT_BRANCH}\n\n${env.GIT_CHANGELOG}"
  env.ORG_GRADLE_PROJECT_BETA_RELEASE_NOTES=releaseNotes

  echo "Release Notes: ${env.ORG_GRADLE_PROJECT_BETA_RELEASE_NOTES}"

  //stage 'Stage Upload To Fabric' 
  //sh "./gradlew crashlyticsUploadDistributionDebug -PBUILD_NUMBER=${env.BUILD_NUMBER}"

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
