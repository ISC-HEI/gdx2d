#!/bin/bash

# Script updated from 'https://github.com/ReadyTalk/swt-bling/blob/master/.utility/push-javadoc-to-gh-pages.sh'

# Must be the master branch (not a PR) and Java JDK 6 (openjdk6)
if [ "$TRAVIS_REPO_SLUG" == "hevs-isi/gdx2d" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_JDK_VERSION" == "openjdk6" ]
then
  echo -e "Publishing latest javadoc using ('$TRAVIS_JDK_VERSION') to 'http://hevs-isi.github.io/gdx2d/javadoc/latest/'\n"

  mkdir -p $HOME/javadoc
  cp -R doc/latest $HOME/javadoc/

  cd $HOME

  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG} gh-pages > /dev/null

  cd gh-pages
  cp -f ./javadoc/latest/stylesheet.css $HOME/stylesheet.css # Backup the remote stylesheet
  git rm -rf ./javadoc/latest
  rm -rf ./javadoc/latest
  cp -Rf $HOME/javadoc/latest ./javadoc/
  cp -f $HOME/stylesheet.css ./javadoc/latest/stylesheet.css # Restore the Java8 stylesheet
  git add -f .
  git commit -m $'Lastest javadoc auto-published.\nTravis build '$TRAVIS_BUILD_NUMBER'.'
  git push -fq origin gh-pages > /dev/null
else
  echo -e "Skipping Javadoc...\n"
fi