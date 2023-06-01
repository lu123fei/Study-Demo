#!/bin/sh

PIDPROC=`ps -ef | grep '${project.name}' | grep -v 'grep'| awk '{print $2}'`

if [ -z "$PIDPROC" ];then
 echo "${project.name} is not running"
 exit 0
fi

echo "PIDPROC: "$PIDPROC
for PID in $PIDPROC
do
if kill -9 $PID
   then echo "process ${project.name}(Pid:$PID) was force stopped at " `date`
fi
done
echo stop finished.