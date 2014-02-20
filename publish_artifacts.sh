#!/bin/sh
echo -e "Publishing artifacts...\n"
cp -R ./target $HOME/artifacts

cd $HOME
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/4242/delmgorb gh-pages > /dev/null

cd gh-pages
git rm -rf ./artifacts
cp -Rf $HOME/artifacts ./artifacts
git add -f ./artifacts
git commit -m "Lastest artifacts on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
git push -fq origin gh-pages > /dev/null

echo -e "Published artifacts to gh-pages.\n"