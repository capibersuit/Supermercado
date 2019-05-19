#!/bin/sh

path=`dirname $0`
. $path/lotes_crm.sh

$JAVA_HOME/bin/java $PARAMS_JAVA -cp "$JARS" ar.gov.mecon.casos_soporte.bin.AgenteNovedades $ARCH_CONF &
echo $! > /var/run/agente_novedades_crm.pid
