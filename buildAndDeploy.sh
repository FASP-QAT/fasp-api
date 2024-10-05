git checkout QAT-469
git pull
mvn clean install
sudo systemctl stop qatApi.service
cp target/*.jar /home/altius/qatApi/qatApi.jar
sudo systemctl start qatApi.service
sudo journalctl -u qatApi.service -f
