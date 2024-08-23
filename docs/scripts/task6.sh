#!/bin/bash
NOW=$(date +"%Y-%m-%d")
#Logfile="sftp-$NOW.log"


#sudo  echo "---------------------------------------------------">> /home/ubuntu/QAT/logs/ARTMIS/task6-$NOW.log

#TODAY=$(date)
#echo "Date: $TODAY" >> /home/ubuntu/QAT/logs/ARTMIS/task6-$NOW.log
# echo "Welcome to Task6 logs" >> /home/ubuntu/QAT/logs/ARTMIS/task6-$NOW.log
if [ -n "$(ls -A /home/ubuntu/QAT/supplyPlan/GFPVAN/*.txt  2>/dev/null)" ]
then

 localpath=/home/ubuntu/QAT/supplyPlan/GFPVAN
 remotepath=
sftp -**** ****@bastion7.e2open.net <<EOF

! echo "Login to GFPVAN SFTP server was successful"  >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log
cd ..
put $localpath/*.txt $remotepath

EOF

echo "Data has been copied from QATPROD GFPVAN to SFTP supplyPlan folder" >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log

 mv /home/ubuntu/QAT/supplyPlan/GFPVAN/*.txt /home/ubuntu/QAT/supplyPlan/GFPVAN/processed

 echo "Data has been moved from QATPROD GFPVAN to QATPROD proceesed folder" >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log



  echo "GFPVAN files for Task3 is Completed for the Date $NOW"
 # echo $Logfile >> /home/ubuntu/QAT/logs/qat/$NOW-sftp.log

 else
 echo "supplyPlan/GFPVAN Folder Is Empty"  >> /home/ubuntu/QAT/logs/ARTMIS/task3-$NOW.log

fi

exit

