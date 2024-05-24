import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, Image } from 'react-native';
import { DrawerContentScrollView, DrawerItemList } from '@react-navigation/drawer';
import AsyncStorage from '@react-native-async-storage/async-storage';

const customDrawerContent = (props) => {
  
  const [userName, setUserName] = useState('NAGARSEVAK');
  const userRole = 'NAGARSEVAK'; // Directly set the value of userRole
  
  useEffect(() => {
    const fetchUserName = async () => {
      try {
        const storedUserName = await AsyncStorage.getItem('userName');
        if (storedUserName) {
          setUserName(storedUserName);
        }
      } catch (error) {
        console.error('Failed to fetch user name from AsyncStorage', error);
      }
    };

    fetchUserName();
  }, []);

  const profilePicUrl = require("../../assets/displayPicture.png"); //require("") is used to import the image from the current directory
  return (
    <DrawerContentScrollView {...props}>
      <View style={styles.drawerHeader}>
        {/* Profile Picture */}
        <Image source={profilePicUrl} style={styles.profilePic} />

        {/* User Name */}
        <Text style={styles.userName}>{userName}</Text>

        {/* User Role */}
        <Text style={styles.userRole}>{userRole}</Text>

      </View>
      {/* Default drawer items */}
      <DrawerItemList {...props} />
    </DrawerContentScrollView>
  );
};

// Styles
const styles = StyleSheet.create({
  drawerHeader: {
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 20,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  profilePic: {
    width: 80,
    height: 80,
    borderRadius: 40, //Changing Profile pic to circle
    marginBottom: 10,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  userRole: {
    fontSize: 14,
    color: '#888', // Light color for userRole
  },
});

export default customDrawerContent;
