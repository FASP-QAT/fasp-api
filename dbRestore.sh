7z x src/main/resources/fasp-Fasponia-db.zip
sed -i 's/localhost/%/g' fasp-Fasponia-db.sql
sed -i 's/root/faspUser/g' fasp-Fasponia-db.sql
mysql -u faspUser -p -h qatuat.ckjjt1wn5lh7.us-east-1.rds.amazonaws.com fasp < fasp-Fasponia-db.sql
rm fasp-Fasponia-db.sql

