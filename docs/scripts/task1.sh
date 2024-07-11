#!/bin/bash

NOW=$(date +"%Y-%m-%d")


echo "---------------------------------------------------">> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log

TODAY= $(date)
echo "Date: $TODAY" >>  /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log
echo "Welcome To Task1 Log" >>  /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log


localpath=/home/ubuntu/QAT/ARTMIS
localpath1=/home/ubuntu/QAT/tmp
remotepath=/FASP/ARTMIS
premotepath=/FASP/processed
rm -f $localpath1/*

sh /home/ubuntu/QAT/script/task1-getFiles.sh
[ "$(ls -A /home/ubuntu/QAT/tmp)" ] && sshpass -p '********' sftp ****@***.***.50.133 <<EOF || echo "ARTMIS folder is empty" >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log

! echo "Login to SFTP server was successful" >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log
get $remotepath/* $localpath


! echo "Data has been copied from SFTP ARTMIS to QATPROD ARTMIS folder" >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log
put $localpath1/* $premotepath


rm $remotepath/*
!echo "Data has been moved from STFP ARTMIS to SFTP proceesed folder" >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log

!echo "Starting consumption of item_data files " >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log 
!chmod 777 /home/ubuntu/QAT/ARTMIS/item_data*
!rm /home/ubuntu/QAT/importProductCatalog
!wget -t 1 --timeout=600 https://api.quantificationanalytics.org/importProductCatalog -O /home/ubuntu/QAT/importProductCatalog
!cat  /home/ubuntu/QAT/importProductCatalog    >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log
!echo "Consumption of item_data files has been completed" >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log

!echo "Starting consumption of order_data and shipment_data files" >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log
!chmod 777 /home/ubuntu/QAT/ARTMIS/order_data*
!chmod 777 /home/ubuntu/QAT/ARTMIS/shipment_data*
!rm /home/ubuntu/QAT/importShipmentData
!wget -t 1 --timeout=600 https://api.quantificationanalytics.org/importShipmentData -O /home/ubuntu/QAT/importShipmentData
!cat  /home/ubuntu/QAT/importShipmentData  >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log
!echo "Consumption of order_data and shipment_data has been completed" >> /home/ubuntu/QAT/logs/ARTMIS/task1-$NOW.log
exit
