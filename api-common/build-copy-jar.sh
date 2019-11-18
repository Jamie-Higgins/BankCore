#!/bin/bash
# Create the api-common jar file from source and copy to required location in desired api project
# Usage: run with argument -b and project name e.g. -b banking-api

declare -a projects=(banking-api)
target_dir='build/libs'

if [ $# -eq 0 ]; then
  echo 'No option supplied, please run again with -b followed by the project you want to copy the jar to i.e. -b banking-api'
fi

in_projects_array() {
  for e in "${projects[@]}"; do
    if [ "$e" == "$project" ]; then
      return 0
    fi
  done
  return 1
}

while getopts ":b:" option;
do
  case $option in
  b)
    project=$OPTARG

    if ! in_projects_array; then
      echo 'You have chosen an invalid project! Please run again with a valid project name...'
      exit 1
    fi

    echo 'Building jar file...'
    ./gradlew clean build
    if [[ $? -ne 0 ]]; then
      echo 'Gradle clean build failed, exiting!'
      exit 1
    fi

    jar_file_name=$(ls $target_dir)

    echo 'Jar file built, copying to desired project directory...'

    if [ ! -d "../$project/$target_dir" ]; then
      mkdir -p ../$project/$target_dir
    fi
    cp $target_dir/$jar_file_name ../$project/$target_dir
    ;;
    :)
      echo "Option -$OPTARG needs an argument i.e. banking-api"
    ;;
    *)
      echo "Invalid option $OPTARG, please run with -b"
      ;;
  esac
done
