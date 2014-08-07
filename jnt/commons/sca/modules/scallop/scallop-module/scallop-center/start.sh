#!/bin/sh

IP=172.16.0.224
SERVICE_HOME=/data/SERVER/scallop-center

LIB_DIR=${SERVICE_HOME}/lib


LOGS_DIR=${SERVICE_HOME}/logs

ARCHIVE_SUFFIX=`date +%Y%m%d-%H%M`

MAIN_CLASS="scallop.center.Server"

JAVA_ARGS="-server -Xms1024m -Xmx1024m -XX:NewSize=100m -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=58 -XX:PermSize=64m -XX:MaxPermSize=64m -XX:ThreadStackSize=256 -Xloggc:${SERVICE_HOME}/logs/gc.log"


CLASSPATH=$CLASSPATH:${SERVICE_HOME}/classes/
files=`ls -1 ${LIB_DIR}`
for file in ${files} ;do
        CLASSPATH=$CLASSPATH:${LIB_DIR}/${file}
done

mv ${LOGS_DIR}/stdout.log ${LOGS_DIR}/stdout.log.${ARCHIVE_SUFFIX}
mv ${LOGS_DIR}/stderr.log ${LOGS_DIR}/stderr.log.${ARCHIVE_SUFFIX}
mv ${LOGS_DIR}/gc.log ${LOGS_DIR}/gc.log.${ARCHIVE_SUFFIX}

java -cp $CLASSPATH ${JAVA_ARGS} ${MAIN_CLASS} -l=$IP -p=8182 1>${SERVICE_HOME}/logs/stdout.log 2>${SERVICE_HOME}/logs/stderr.log&

echo "scallop center starting..."