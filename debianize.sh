#! /bin/bash -x

ROOT=$(pwd)

mvn clean package
mkdir target/debian
cp target/Schrumpf-1.0-SNAPSHOT-dist.tar.gz target/debian
cd target/debian
tar -xzvf Schrumpf-1.0-SNAPSHOT-dist.tar.gz
mv Schrumpf-1.0-SNAPSHOT schrumpf-1.0
tar -czvf schrumpf_1.0.orig.tar.gz schrumpf-1.0
cp -r ../../src/main/debian schrumpf-1.0
mkdir schrumpf-1.0/resources
cp -r ../../src/main/resources/de/tynne/schrumpf/ui/Logo-Entwurf-80x60.png schrumpf-1.0/resources/schrumpf.png

cd schrumpf-1.0
rm -fr build/ && debuild -us -uc
