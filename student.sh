#!/bin/sh

# Exit on error
set -e

mvn clean 

#Package library
cd gdx2d-library
mvn compile package
cd ..

#Fill student directory
rm -rf student
mkdir student
cp -r pom.xml student
cp -r gdx2d-demoDesktop student
cp -r gdx2d-helloDesktop student
cp -r lib student
cp gdx2d-library/gdx2d-desktop/target/*.jar student/lib
sed -e 's/<!--STUDENT_RELEASE//g' -e 's/STUDENT_RELEASE-->//g' student/pom.xml > student/pom.xml.tmp
mv student/pom.xml.tmp student/pom.xml

echo 'done :-)'
