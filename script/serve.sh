#!/bin/bash

echo "Serve jekyll"
bundle exec jekyll serve -w

sh ./clean.sh