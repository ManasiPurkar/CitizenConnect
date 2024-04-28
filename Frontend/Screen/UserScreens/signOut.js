import React from 'react';
import { View, Text, Button, StyleSheet, TouchableOpacity } from 'react-native';

const SignOutScreen = ({ navigation }) => {

  const handleSignOut = () => {
    // Implement your sign out logic here
    // For example, clearing user data, navigating to the sign-in screen, etc.
    navigation.navigate('../SignInPage'); // Navigate to your sign-in screen
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