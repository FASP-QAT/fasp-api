sed -i 's/localhost/%/g' dump.sql
sed -i 's/root/faspUser/g' dump.sql
mysql -u faspUser -p -h qatuat.ckjjt1wn5lh7.us-east-1.rds.amazonaws.com fasp < dump.sql
