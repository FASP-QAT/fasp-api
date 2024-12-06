 git checkout master
 git pull
 mvn clean install
cp target/*.jar ../../qatApi/
sudo systemctl stop qatApi.service
cp target/*.jar ../../qatApi/qatApi.jar
sudo systemctl start qatApi.service
sudo journalctl -u qatApi.service -f