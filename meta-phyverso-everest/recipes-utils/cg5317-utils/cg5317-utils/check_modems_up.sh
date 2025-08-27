#!/bin/sh
/root/examples/management_tool/management_tool -c get_device_version -a seth0
state_modem0=$?
/root/examples/management_tool/management_tool -c get_device_version -a seth1
state_modem1=$?

while test "$state_modem0" != "0" || test "$state_modem1" != "0"
do
echo "State Modem0: $state_modem0 State Modem1: $state_modem1"
/bin/sleep 1

/root/examples/management_tool/management_tool -c get_device_version -a seth0
state_modem0=$?

/root/examples/management_tool/management_tool -c get_device_version -a seth1
state_modem1=$?
done
exit 0
