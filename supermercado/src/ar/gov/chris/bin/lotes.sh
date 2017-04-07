#!/bin/sh

DIRNAME=`/usr/bin/dirname $0`
PATH=$PATH:$DIRNAME
TOMCAT=/var/lib/tomcat7
APPDIR=$TOMCAT/webapps/supermercado/WEB-INF
DIRJARS=$APPDIR/lib
SERVLET=$TOMCAT/common/lib/servlet-api.jar
BUILD=$APPDIR/classes


[ ! -d $TOMCAT ] && echo "ERROR: El directorio $TOMCAT no existe." && echo "Está mal configurada la variable TOMCAT, revise el script" && exit 1
[ ! -d $APPDIR ] && echo "ERROR: El directorio $APPDIR no existe." && echo "Está mal configurada la variable APPDIR, revise el script" && exit 1
[ ! -d $DIRJARS ] && echo "ERROR: El directorio $DIRJARS no existe." && echo "Está mal configurada la variable DIRJARS, revise el script" && exit 1
[ ! -f $SERVLET ] && echo "ERROR: El archivo $SERVLET no existe." && echo "Está mal configurada la variable SERVLET, revise el script" && exit 1
[ ! -d $BUILD ] && echo "ERROR: El directorio $BUILD no existe." && echo "Está mal configurada la variale BUILD, revise el script" && exit 1

JARS="$SERVLET:$BUILD"

for k in `ls -1 $DIRJARS/*.jar`;
do
        JARS="$JARS:$k"
done

JAVA_HOME=/usr/bin/; export JAVA_HOME
PARAMS_JAVA="-Duser.timezone=GMT-3"; export PARAMS_JAVA


export JARS
