#!/bin/sh
MAIN_CLASS="com.fansz.auth.AuthStarter"
  
#  
cd `dirname $0`/..  
BASE_DIR="`pwd`"
echo  "$BASE_DIR"

  
# cat 
export JAVA_HOME="/usr/local/jdk1.7.0_79"
   
  
JAVA_OPTS="$JAVA_OPTS -server -Xms2048m -Xmx2048m -XX:PermSize=256m -XX:MaxPermSize=512m"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=200"  
#JAVA_OPTS="$JAVA_OPTS -XX:+UnlockExperimentalVMOptions -XX:G1LogLevel=finest -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime"
  
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError"  
JAVA_OPTS="$JAVA_OPTS -Xloggc:$BASE_DIR/logs/gc.log"
AUTH_PID="$BASE_DIR/logs/auth.pid"

if [ ! -f "$JAVA_HOME/bin/java" ]; then  
  echo "please set JAVA_HOME"  
  exit 1;  
fi  
  
LOG_PATH="$BASE_DIR/logs" 
AUTH_OUT="$LOG_PATH/auth.out"
  
if [ ! -d "$LOG_PATH" ]; then                                                                                                                                                   
    mkdir $LOG_PATH  
fi  

#star auth server
nohup "$JAVA_HOME"/bin/java  $JAVA_OPTS  -Dapplication.root="$BASE_DIR" -Dlog4j.configuration="file:$BASE_DIR/conf/log4j.xml" -classpath "$BASE_DIR/lib/*" "$MAIN_CLASS"  >> "$AUTH_OUT" 2>&1 &

echo $! > "$AUTH_PID"



