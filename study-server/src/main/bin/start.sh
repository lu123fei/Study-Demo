#!/bin/sh

BASEDIR=`dirname $0`/..
BASEDIR=`(cd "$BASEDIR"; pwd)`

# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

LOGDIR="/export/Logs/${project.name}"
LOGFILE="$LOGDIR"/main.log
if [ ! -d "$LOGDIR" ] ; then
    mkdir "$LOGDIR"
fi

LIB_DIR="$BASEDIR"/lib
if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute $JAVACMD"
  exit 1
fi

if [ -z "$OPTS_MEMORY" ] ; then
    OPTS_MEMORY="${jvmOpts}"
fi

echo $BASEDIR

nohup "$JAVACMD" ${PFINDER_AGENT:-} \
  $JAVA_OPTS \
  $OPTS_MEMORY \
  -Dloader.path=file:$BASEDIR/conf/ \
  -Dloader.debug=true \
  -Dbasedir="$BASEDIR" \
  -Dfile.encoding="UTF-8" \
  $DEPLOY_ENV \
  -jar $LIB_DIR/${project.name}-${project.version}.jar \
  "$@" >$LOGFILE 2>&1 &

echo "${project.name} started"