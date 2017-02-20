pipeline {
    agent any
    options {
        // Keep the 10 most recent builds
        buildDiscarder(logRotator(numToKeepStr:'10'))
    }
    stages {
        stage ('Start') {
              steps {
                // send build started notifications
                sendNotifications 'STARTED'
              }
        }

        stage("Stage Checkout") {
            steps {
                checkout scm
                sh 'git submodule update --init'

                sh 'git rev-parse --verify HEAD > jenkins_pipeline_output.temp'
                //def commitHash = readFile('jenkins_pipeline_output.temp').split("\r?\n")[0]
                //env.GIT_COMMIT = commitHash
            }
        }

        stage('Stage Lint') {
            steps {
                checkLint()
            }
        }

        stage('Stage Build') {
            steps {
                //branch name from Jenkins environment variables
                echo "My branch is: ${env.BRANCH_NAME}"

                //build your gradle flavor, passes the current build number as a parameter to gradle
                sh "./gradlew clean assemble -PBUILD_NUMBER=${env.BUILD_NUMBER}"
            }
        }

        stage('Stage Unit Test') {
            steps {
                runTestAndArchiveResult(':app:test', 'app/build/test-results', '*/TEST-*.xml')
            }
        }

        stage('Stage Archive') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/*.apk', fingerprint: true
            }
        }

        stage('Stage Upload To Fabric') {
            steps {
                sh "./gradlew crashlyticsUploadDistributionDebug -PBUILD_NUMBER=${env.BUILD_NUMBER}"
            }
        }
    }
    post {
        always {
          sendNotifications currentBuild.result
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

def sendNotifications(String buildStatus = 'STARTED') {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = "${subject} (${env.BUILD_URL})"
  def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }

  // Send notifications
  slackSend (color: colorCode, message: summary)

  emailext (
      to: 'ducva2410@gmail.com',
      subject: subject,
      body: details,
      recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
}

void checkLint() {
    def pfs = ['']
    def bts = ['release', 'debug']
    List<String> variants = []
    pfs.each { pf -> bts.each{ bt -> variants.add(pf + (pf.isEmpty() ? bt : bt.capitalize())) } }

    // List#collect メソッドを使いたいが、groovy-cps ライブラリのバグで Pipeline 上でうまく動かない。
    // See : https://issues.jenkins-ci.org/browse/JENKINS-26481
    List<GString> gradleTasks = []
    List<GString> outputFiles = []
    variants.each {
        gradleTasks.add(":app:lint${it.capitalize()}")
        outputFiles.add("lint-results-${it}.html")
    }

    // テスト失敗時にも結果を保存するように try-catch する。
    Throwable error = null
    try { sh "./gradlew --stacktrace ${gradleTasks.join(' ')}" } catch (e) { error = e }
    try {
       publishHTML([
                            target: [
                                    reportName: 'Android Lint Report',
                                    reportDir: 'app/build/outputs/',
                                    reportFiles: outputFiles.join(','),
                            ],
                            // Lint でエラーが発生した場合はリポートファイルがないことを許容する。
                            allowMissing: error != null,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                    ])
       } catch (e) { if (error == null) error = e }
       if (error != null) throw error
}

void runTestAndArchiveResult(String gradleTestTask, String resultsDir, String resultFilesPattern) {
    sh "rm -rf ${resultsDir}"

    // テスト失敗時にも結果を保存するように try-catch する。
    Throwable error = null
    try { sh "./gradlew --stacktrace ${gradleTestTask}" } catch (e) { error = e }
    try {
        step $class: 'JUnitResultArchiver', testResults: "${resultsDir}/${resultFilesPattern}"
    } catch (e) { if (error == null) error = e }
    if (error != null) throw error
}