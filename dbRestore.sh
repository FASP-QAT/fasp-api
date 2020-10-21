7z x src/main/resources/fasp-db.zip
sed -i 's/localhost/%/g' fasp-db.sql
sed -i 's/root/faspUser/g' fasp-db.sql
mysql -u faspUser -p -h qatuat.ckjjt1wn5lh7.us-east-1.rds.amazonaws.com fasp < fasp-db.sql
rm fasp-db.sql

