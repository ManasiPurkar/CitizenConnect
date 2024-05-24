import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const SignOutScreen = ({ navigation }) => {

  const handleSignOut = () => {
    // Call the function to clear AsyncStorage
    clearAsyncStorage();
    navigation.navigate('SignInPage'); // Navigate to your sign-in screen
  };
  // To clear all data stored in AsyncStorage
  const clearAsyncStorage = async () => {
    try {
        await AsyncStorage.clear();
        console.log('AsyncStorage cleared successfully.');
    } catch (error) {
        console.error('Error clearing AsyncStorage:', error);
    }
  };
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Are you sure you want to sign out?</Text>
        <TouchableOpacity 
            style={[styles.button]} 
            onPress={handleSignOut} 
        > 
            <Text style={styles.buttonText}>Sign Out</Text> 
        </TouchableOpacity> 
    </View>
  );
};

export default SignOutScreen;

const styles = StyleSheet.create({
    button: { 
        backgroundColor: '#40E0D0',
        borderRadius: 8, 
        paddingVertical: 10, 
        alignItems: 'center', 
        marginTop: 16, 
        marginBottom: 12, 
    },
    buttonText: { 
        color: '#fff', 
        fontWeight: 'bold', 
        fontSize: 16, 
    },
});