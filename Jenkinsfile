pipeline {
	agent any
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
				branch 'master'
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

