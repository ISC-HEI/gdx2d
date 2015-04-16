#!/bin/bash

# Script updated from 'https://github.com/ReadyTalk/swt-bling/blob/master/.utility/push-javadoc-to-gh-pages.sh'

if [ "$TRAVIS_REPO_SLUG" == "hevs-isi/gdx2d" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]
then
  echo -e "Publishing latest javadoc to 'http://hevs-isi.github.io/gdx2d/javadoc/latest/'\n"

  cp --parents -R doc/latest $HOME/javadoc/latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG} gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc/latest
  cp -Rf $HOME/javadoc/latest ./javadoc/latest
  git add -f .
  git commit -m "Lastest javadoc auto-published.\n Travis build $TRAVIS_BUILD_NUMBER."
  git push -fq origin gh-pages > /dev/null

  echo -e "Latest javadoc published.\n"
else
  echo -e "Javadoc not published.\n"
fi