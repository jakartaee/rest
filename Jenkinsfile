pipeline {
	agent any
	triggers {
		pollSCM('H H * * *')
	}
	tools {
		jdk 'jdk1.8.0-latest'
		maven 'apache-maven-latest'
	}
	environment {
		MVN = 'mvn -B'
	}
	stages {
		stage('Nightly Build') {
			when {
				anyOf {
					branch 'EE4J_8'
					branch 'master'
				}
			}
			steps {
				dir ('jaxrs-api') {
					sh "$MVN deploy"
				}
				dir ('examples') {
					sh "$MVN deploy"
				}
			}
		}
	}
}

