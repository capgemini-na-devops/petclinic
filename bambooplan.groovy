project(key: 'MYP1', name: 'Petclinic_Project_Demos8') {
    plan(key: 'PETS1', name: 'Petclinic_Plans8') {
        description ''
        enabled true
            
              scm{
                linkedRepository("Petclinic")
                 }
		
		triggers {
		remote() {
        description 'my remote trigger'
        repositories 'Petclinic'
        ipAddresses '0.0.0.0/0'
        }
	
		polling() {
        description 'mypollsched'
        repositories 'Petclinic'
        scheduled {
            cronExpression '* * * ? * *'
        }
    }
    }
		notifications {
        email(event: NotificationEvent.FIRST_FAILED_JOB_FOR_PLAN) {
        email: 'pooja-k.kulkarni@capgemini.com'
        }
		email(event: NotificationEvent.ALL_JOBS_COMPLETED) {
        email: 'pooja-k.kulkarni@capgemini.com'
        }
		email(event: NotificationEvent.ALL_BUILDS_COMPLETED) {
        email: 'pooja-k.kulkarni@capgemini.com'
        }
		email(event: NotificationEvent.FAILED_JOBS_AND_FIRST_SUCCESSFUL) {
        email: 'pooja-k.kulkarni@capgemini.com'
        }
		email(event: NotificationEvent.ALL_JOBS_COMPLETED) {
        email: 'pooja-k.kulkarni@capgemini.com'
        }
		email(event: NotificationEvent.JOB_ERROR) {
        email: 'pooja-k.kulkarni@capgemini.com'
        }
    }
              scm{
                linkedRepository("Petclinic")
                 }
      
         stage(name: 'Development Stage') {
            description ''
            manual false
			
			branches {
	  branch(name: 'development') {
            vcsBranchName 'development/Petclinic'
        }
		}
        
            job(key: 'JOB1', name: 'Petclinic_Tomcat_Dev') {
                description ''
                enabled true
            
                tasks { 
				     checkout() {
						description 'checkout repo'
						forceCleanBuild true
                       		
      scm{
        linkedRepository("Petclinic")
      }				
						}
                    custom(pluginKey: 'com.atlassian.bamboo.plugins.vcs:task.vcs.checkout') {
                        description 'github'
                        enabled true
                        isFinal false
                        configure(
                             'cleanCheckout': '',
                             'selectedRepository_0': 'defaultRepository',
                             'checkoutDir_0': '',
                        )
                    } 
                    
                    custom(pluginKey: 'com.atlassian.bamboo.plugins.maven:task.builder.mvn3') {
                        description 'Build'
                        enabled true
                        isFinal false
                        configure(
                             'projectFile': '',
                             'goal': 'clean package',
                             'testDirectoryOption': 'standardTestDirectory',
                             'environmentVariables': '',
                             'testResultsDirectory': '**/target/surefire-reports/*.xml',
                             'buildJdk': 'JDK 1.8',
                             'label': 'Maven3_Home',
                             'testChecked': '',
                             'workingSubDirectory': '',
                             'useMavenReturnCode': 'false',
                        )
                    } 
                    custom(pluginKey: 'ch.mibex.bamboo.sonar4bamboo:sonar4bamboo.maven3task') {
                        description 'sonarqube'
                        enabled false
                        isFinal false
                        configure(
                             'incrementalFileForInclusionList': '',
                             'chosenSonarConfigId': '1',
                             'useGradleWrapper': '',
                             'sonarMainBranch': 'master',
                             'useNewGradleSonarQubePlugin': '',
                             'sonarJavaSource': '',
                             'sonarProjectName': '',
                             'buildJdk': 'JDK 1.8',
                             'gradleWrapperLocation': '',
                             'sonarLanguage': '',
                             'sonarSources': '',
                             'useGlobalSonarServerConfig': 'true',
                             'incrementalMode': '',
                             'sonarPullRequestAnalysis': '',
                             'failBuildForBrokenQualityGates': '',
                             'msbuilddll': '',
                             'sonarTests': '',
                             'incrementalNoPullRequest': 'incrementalModeFailBuildField',
                             'failBuildForSonarErrors': '',
                             'sonarProjectVersion': '',
                             'sonarBranch': '',
                             'executable': 'Maven3_Home',
                             'illegalBranchCharsReplacement': '_',
                             'failBuildForTaskErrors': 'true',
                             'incrementalModeNotPossible': 'incrementalModeRunFullAnalysis',
                             'sonarJavaTarget': '',
                             'environmentVariables': '',
                             'incrementalModeGitBranchPattern': '(.*/)?feature/.*',
                             'legacyBranching': '',
                             'replaceSpecialBranchChars': '',
                             'additionalProperties': '',
                             'autoBranch': '',
                             'sonarProjectKey': '',
                             'incrementalModeBambooUser': '',
                             'overrideSonarBuildConfig': '',
                             'workingSubDirectory': '',
                        )
                    } 
                    custom(pluginKey: 'com.atlassian.bamboo.plugins.scripttask:task.builder.script') {
                        description 'nexus'
                        enabled true
                        isFinal false
                        configure(
                             'argument': '',
                             'scriptLocation': 'INLINE',
                             'environmentVariables': '',
                             'scriptBody': '''curl -v -u admin:admin123 --upload-file ${bamboo.build.working.directory}/target/petclinic.war http://18.236.232.129:8081/nexus/content/repositories/petclinic_repo/releases/org/foo/${bamboo.buildNumber}/petclinic.war
                    
                    cp /usr/local/sonatype-work/nexus/storage/petclinic_repo/releases/org/foo/${bamboo.buildNumber}/petclinic.war ${bamboo.build.working.directory}/target''',
                             'interpreter': 'LEGACY_SH_BAT',
                             'script': '',
                             'workingSubDirectory': '',
                        )
                    } 
                    custom(pluginKey: 'com.atlassian.bamboo.plugins.tomcat.bamboo-tomcat-plugin:deployAppTask') {
                        description ''
                        enabled false
                        isFinal false
                        configure(
                             'appVersion': '',
                             'tomcatUrl': 'http://54.202.213.82:8090/manager',
                             'warFilePath': '/target/petclinic.war',
                             'tomcatUsername': 'admin',
                             'deploymentTag': '',
                             'encTomcatPassword': '6SSTqKJm4iU=',
                             'appContext': '/mypet',
                             'tomcat6': '',
                        )
                    } 
                }
            
                artifacts { 
                    definition(name: 'PetClinic', copyPattern: '**/*.war') {
                        location 'target'
                        shared true
                    } 
            
                }
            } 

        } 

        deploymentProject(name: 'Deployment1 for Petclinic_Project_Demo') {
            description ''
            
            environment(name: 'Development') {
                description ''
				triggers {
				afterSuccessfulBuildPlan() {
				customPlanBranchName 'development'
				}
				}
                tasks { 
                    custom(pluginKey: 'com.atlassian.bamboo.plugins.bamboo-artifact-downloader-plugin:cleanWorkingDirectoryTask') {
                        description ''
                        enabled true
                        isFinal false
                        configure(
                        )
                    } 
                    custom(pluginKey: 'com.atlassian.bamboo.plugins.bamboo-artifact-downloader-plugin:artifactdownloadertask') {
                        description 'Download release contents'
                        enabled true
                        isFinal false
                        configure(
                             'sourcePlanKey': 'MYP-PETS',
                             'artifactName_0': 'PetClinic',
                             'artifactId_0': '4358152',
                             'localPath_0': '',
                        )
                    } 
                    custom(pluginKey: 'com.atlassian.bamboo.plugins.tomcat.bamboo-tomcat-plugin:deployAppTask') {
                        description ''
                        enabled true
                        isFinal false
                        configure(
                             'appVersion': '',
                             'tomcatUrl': 'http://54.202.213.82:8090/manager',
                             'warFilePath': 'petclinic.war',
                             'tomcatUsername': 'admin',
                             'deploymentTag': '',
                             'encTomcatPassword': '6SSTqKJm4iU=',
                             'appContext': '/mypet',
                             'tomcat6': '',
                        )
                    } 
                }
            } 
            
        } 
    }
}
