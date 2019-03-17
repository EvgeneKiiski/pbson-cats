#!/usr/bin/env bash

rm -fr repository/*
sbt clean compile test publish
./jfrog bt u /Users/evg/work/pbson/repository/ twistedlogic/pbson/pbson-cats/v0.0.1 ru/twistedlogic/pbson-cats_2.12/0.0.1/