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
				configFileProvider([
					configFile(fileId: 'a79c37a7-bfd9-4699-8077-f992027b2c1b', targetLocation: '/home/jenkins/.m2/settings.xml'),
					configFile(fileId: 'b78b4e15-215f-42bc-81ac-53cd3e2ef708', targetLocation: '/home/jenkins/.m2/')
				]) {
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
}

