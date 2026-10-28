#!/bin/bash
which adb > /dev/null
if (( $? != 0 ));
then
	echo "Error: No adb available."
	echo "Make sure that you've installed adb."
	read -p "Press any key to continue..." -n1 -s
	exit 1
fi
cd ~
declare -i apk_count=0
for FILE in `ls ~`;
do
	if [ -f "$FILE" ];
	then
		echo $FILE | grep ".apk\$" > /dev/null
		if [ "$?" -eq 0 ];
		then
			apk_files[$apk_count]=$FILE
			apk_count=$((apk_count+1))
		fi
	fi
done
if (( $apk_count == 0 ))
then
	echo "No APK files found in ~."
	read -p "Press any key to continue..." -n1 -s
	cd - > /dev/null
	exit 1
fi
echo "Available APK files:"
echo "===================="
declare -i counter=1
for FILE in "${apk_files[@]}";
do
	echo -n $counter
	echo -n ". "
	echo $FILE
	((++counter))
done
echo "0. Exit"
while [ 0 -eq 0 ];
do
	echo "Select APK to install (0-$apk_count): "
	read counter
	if [ $((counter)) -eq 0 ];
	then
		echo "Goodbye!"
		cd - > /dev/null
		exit 0
	elif [ $((counter)) -gt $apk_count ];
	then
		echo "Invalid selection. Please try again."
		continue
	fi 
	echo "Installing APK..."
	selected_apk=${apk_files[$(($((counter))-1))]}
	echo "Installing: $selected_apk"
	adb install $selected_apk
	if [ $? -eq 0 ];
	then
		echo "Successfully installed!"
	else
		echo "Installation failed!"
	fi
	read -p "Press any key to continue..." -n1 -s
	echo ""
done
