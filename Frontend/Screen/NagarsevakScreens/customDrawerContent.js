import React from 'react';
import { View, Text, StyleSheet, Image } from 'react-native';
import { DrawerContentScrollView, DrawerItemList } from '@react-navigation/drawer';
import { MaterialIcons } from '@expo/vector-icons';

const customDrawerContent = (props) => {
  
  const userName = "NagarSevak"; // to be fetched from backend
  //const profilePicUrl = "./displayPicture.png"; 
  const profilePicUrl = require("../../assets/displayPicture.png"); //require("") is used to import the image from the current directory
  return (
    <DrawerContentScrollView {...props}>
      <View style={styles.drawerHeader}>
        {/* Profile Picture */}
        <Image source={profilePicUrl} style={styles.profilePic} />

        {/* User Name */}
        <Text style={styles.userName}>{userName}</Text>
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
});

export default customDrawerContent;
