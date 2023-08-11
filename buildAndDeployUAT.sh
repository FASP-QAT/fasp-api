git checkout uat2
git pull
mvn clean install
sudo systemctl stop qatApi.service
cp target/*.jar ../../qatApi/qatApi.jar
sudo systemctl start qatApi.service
sudo journalctl -u qatApi.service -f
