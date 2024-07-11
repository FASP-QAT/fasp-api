#!/bin/bash

NOW=$(date +"%Y-%m-%d")


echo "---------------------------------------------------">> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log

TODAY=$(date)
echo "Date: $TODAY" >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log
echo "Welcome to Task2 logs" >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log
cd /home/ubuntu/QAT/ARTMIS/
rm exportProgramData
rm exportOrderData
rm exportShipmentLinkingData
wget https://api.quantificationanalytics.org/exportProgramData
wget https://api.quantificationanalytics.org/exportOrderData
wget https://api.quantificationanalytics.org/exportShipmentLinkingData
cat exportProgramData    >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log
cat exportOrderData       >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log
cat exportShipmentLinkingData       >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log
rm exportProgramData
rm exportOrderData
rm exportShipmentLinkingData
if [ -n "$(ls -A /home/ubuntu/QAT/supplyPlan/*.csv  2>/dev/null)" ]
then

localpath=/home/ubuntu/QAT/supplyPlan
remotepath=/FASP/supplyPlan
 sshpass -p '*****' sftp ***@***.***.50.133 <<EOF
! echo "Login to SFTP server was successful"  >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log
put $localpath/*.csv $remotepath
EOF

echo "Data has been copied from QATPROD supplyPlan to SFTP supplyPlan folder" >>/home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log

mv /home/ubuntu/QAT/supplyPlan/*.csv  /home/ubuntu/QAT/supplyPlan/processed

echo "Data has been moved from QATPROD supplyPlan to QATPROD proceesed folder" >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log

echo "Task2 is Completed for the Date $NOW" >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log



else
  echo "CSV  SupplyPlan Folder Is Empty "  >> /home/ubuntu/QAT/logs/ARTMIS/task2-$NOW.log
fi
exit

