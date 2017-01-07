mkdir ~/wpilib
echo "version=current\nteam-number=4761" > ~/wpilib/wpilib.properties
wget https://gist.githubusercontent.com/simon-andrews/0127707da461e543cb76/raw/dl_wpilibjars.py
python3 dl_wpilibjars.py --dest ~/wpilib/java/current --members ant/ant-classloadertask.jar \
	ant/ant-contrib.jar \
	ant/build.properties \
	ant/build.xml \
	ant/frcdebug \
	ant/jsch-0.1.50.jar \
	ant/robotCommand \
	ant/robotDebugCommand \
	lib/WPILib.jar \
	lib/NetworkTables.jar
rm dl_wpilibjars.py
