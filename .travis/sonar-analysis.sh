#!/bin/sh
# Exit on failure
set -e

# Adapted from https://github.com/bellingard/multi-language-project/blob/master/runSonarQubeAnalysis.sh
# This assumes that the following variable is defined:
# - SONAR_TOKEN    => token of a user who has the "Execute Analysis" permission on the SQ server

if [ "$TRAVIS_JDK_VERSION" != "openjdk6" ]; then
	echo "Skipping analysis: wrong JDK. Expected 'openjdk6' but was '$TRAVIS_JDK_VERSION'."

# And run the analysis
# It assumes that the project uses Maven and has a POM at the root of the repo
elif [ "$TRAVIS_BRANCH" = "master" ] && [ "$TRAVIS_PULL_REQUEST" = "false" ]; then
	# => This will run a full analysis of the project and push results to the SonarQube server.
	#
	# Analysis is done only on master so that build of branches don't push analyses to the same project and therefore "pollute" the results
	echo "Starting analysis by SonarQube..."
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar -B -e -V \
		-Dsonar.host.url=https://nemo.sonarqube.org \
		-Dsonar.login=$SONAR_TOKEN

elif [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ -n "${GITHUB_TOKEN-}" ]; then
	# => This will analyse the PR and display found issues as comments in the PR, but it won't push results to the SonarQube server
	#
	# For security reasons environment variables are not available on the pull requests
	# coming from outside repositories
	# http://docs.travis-ci.com/user/pull-requests/#Security-Restrictions-when-testing-Pull-Requests
	# That's why the analysis does not need to be executed if the variable GITHUB_TOKEN is not defined.
	echo "Starting Pull Request analysis by SonarQube..."
	mvn clean package sonar:sonar -B -e -V \
		-Dsonar.host.url=https://nemo.sonarqube.org \
		-Dsonar.login=$SONAR_TOKEN \
		-Dsonar.analysis.mode=preview \
		-Dsonar.github.oauth=$GITHUB_TOKEN \
		-Dsonar.github.repository=$TRAVIS_REPO_SLUG \
		-Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST
fi
# When neither on master branch nor on a non-external pull request => nothing to do