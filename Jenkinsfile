pipeline {
	agent any
	triggers {
		pollSCM('H H * * *')
	}
	tools {
		jdk 'openjdk-jdk21-latest'
		maven 'apache-maven-latest'
	}
	environment {
		MVN = 'mvn -B'
	}
	stages {
		stage('Nightly Build') {
			when {
				anyOf {
					branch 'master'; branch 'release-4.0'
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
