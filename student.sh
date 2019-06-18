#!/bin/sh

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
sed -i 's/<!--STUDENT_RELEASE//g' student/pom.xml
sed -i 's/STUDENT_RELEASE-->//g' student/pom.xml