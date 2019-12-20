pipeline {
	agent any
	triggers {
		pollSCM('H H * * *')
	}
	tools {
		jdk 'openjdk-latest'
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
				dir ('jaxrs-spec') {
					sh "$MVN deploy"
				}
			}
		}
	}
}
