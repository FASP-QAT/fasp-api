#!/bin/bash
NOW=$(date +"%Y-%m-%d")
Logfile="sftp-$NOW.log"


 echo "---------------------------------------------------">> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log
TODAY=$(date)
echo "Date: $TODAY" >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log
echo "Welcome to Task3 logs" >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log
rm /home/ubuntu/QAT/supplyPlan/exportSupplyPlan*
wget -t 1 --timeout=6000 https://api.quantificationanalytics.org/exportSupplyPlan -O /home/ubuntu/QAT/supplyPlan/exportSupplyPlan
cat /home/ubuntu/QAT/supplyPlan/exportSupplyPlan >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log

if [ -n "$(ls -A /home/ubuntu/QAT/supplyPlan/Artmis/*.json  2>/dev/null)" ]
then

localpath=/home/ubuntu/QAT/supplyPlan/Artmis
remotepath=/FASP/supplyPlan
 sshpass -p '*****' sftp ***@***.***.50.133 <<EOF
! echo "Login to SFTP server was successful"  >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log
put $localpath/*.json $remotepath

EOF
cp $localpath/*.json  /home/ubuntu/QAT/sharepoint 
sudo rclone copy  /home/ubuntu/QAT/sharepoint  sharepoint1:supplyPlan  
echo "JSON Data has been copied from QATPROD supplyPlan to Sharepoint supplyPlan folder" >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log
 rm /home/ubuntu/QAT/sharepoint/*.*
echo "JSON Data has been copied from QATPROD supplyPlan to SFTP supplyPlan folder" >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log

mv /home/ubuntu/QAT/supplyPlan/Artmis/*.json  /home/ubuntu/QAT/supplyPlan/Artmis/processed

echo "SupplyPlans have been moved from QAT/ARTMIS supplyPlan to QATPROD proceesed folder" >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log



echo "ARTMIS files for Task3 is Completed for the Date $NOW"
echo $Logfile >> /home/ubuntu/QAT/logs/qat/$NOW-sftp.log

else
  echo "supplyPlan/Artmis Folder Is Empty "  >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log

fi
sh /home/ubuntu/QAT/script/task6.sh

exit

