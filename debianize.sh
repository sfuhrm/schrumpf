#! /bin/bash -x

ROOT=$(pwd)
VERSION=1.0.1

mvn clean package
mkdir target/debian
cp target/Schrumpf-$VERSION-SNAPSHOT-dist.tar.gz target/debian
cd target/debian
tar -xzvf Schrumpf-$VERSION-SNAPSHOT-dist.tar.gz
mv Schrumpf-$VERSION-SNAPSHOT schrumpf-$VERSION
tar -czvf schrumpf_$VERSION.orig.tar.gz schrumpf-$VERSION
cp -r ../../src/main/debian schrumpf-$VERSION
mkdir schrumpf-$VERSION/resources
cp -r ../../src/main/resources/de/sfuhrm/schrumpf/ui/Logo-Entwurf-80x60.png schrumpf-$VERSION/resources/schrumpf.png

cd schrumpf-$VERSION
rm -fr build/ && debuild -us -uc
