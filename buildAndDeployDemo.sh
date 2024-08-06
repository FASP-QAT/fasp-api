git checkout demo
git pull
mvn clean install
sudo systemctl stop demoApi.service
cp target/*.jar ../../qatApi/demoApi.jar
sudo systemctl start demoApi.service
sudo journalctl -u demoApi.service -f
