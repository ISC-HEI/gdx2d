#!/bin/bash

# Script updated from 'https://github.com/ReadyTalk/swt-bling/blob/master/.utility/push-javadoc-to-gh-pages.sh'

# Publish the gdx2d API Javadoc generated using Maven
if [ "$TRAVIS_REPO_SLUG" == "hevs-isi/gdx2d" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ]
then
  echo -e "Publishing latest javadoc using ('$TRAVIS_JDK_VERSION') to 'https://hevs-isi.github.io/gdx2d/javadoc/latest/'\n"

  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG} gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc/latest
  rm -rf ./javadoc/latest

  cp -Rf ../gdx2d-library/target/apidocs ./javadoc/latest
  git add -f .
  git commit -m $'Latest gdx2d API Javadoc auto-published.\nTravis build '$TRAVIS_BUILD_NUMBER'.'
  git push -fq origin gh-pages > /dev/null
else
  echo -e "Skipping publishing gdx2d API Javadoc...\n"
fi
