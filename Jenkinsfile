pipeline {
	agent any
	triggers {
		pollSCM('H H * * *')
	}
	tools {
		jdk 'openjdk-jdk11-latest'
		maven 'apache-maven-latest'
	}
	environment {
		MVN = 'mvn -B'
	}
	stages {
		stage('Nightly Build') {
			when {
				anyOf {
					branch 'master'
                                        branch 'release-prep2'
				}
			}
			steps {
				dir ('.') {
					sh "$MVN deploy"
				}
			}
		}
	}
}
