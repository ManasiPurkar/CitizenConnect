import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, TextInput, View, Button, Alert, TouchableOpacity } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;

export default function ChangePassword() {
    const [currentPassword, setCurrentPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');

    useEffect(() => {
        getEmailFromStorage();
    }, []);

    const getEmailFromStorage = async () => {
        try {
            const storedEmail = await AsyncStorage.getItem('userEmail');
            if (storedEmail !== null) {
                setEmail(storedEmail);
            }
        } catch (error) {
            console.error('Error fetching email from AsyncStorage:', error);
        }
    };

    const handleChangePassword = async () => {
        if (!passwordRegex.test(newPassword)) {
            Alert.alert(
                'Invalid Password',
                'Password must contain at least one lowercase letter, one uppercase letter, one numeric digit, one special character, and be at least 6 characters long.'
            );
            return;
        }

        if (newPassword !== confirmPassword) {
            Alert.alert('Passwords Mismatch', 'New password and confirm password must match.');
            return;
        }

        try {
            console.log(email);
            console.log(currentPassword);
            console.log(newPassword);
            console.log(confirmPassword);
            const response = await fetch('http://172.16.145.13:9093/user/change-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: email,
                    oldPassword: currentPassword,
                    newPassword: newPassword,
                    confirmPassword: confirmPassword,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to update password');
            }

            // Reset input fields
            setCurrentPassword('');
            setNewPassword('');
            setConfirmPassword('');

            Alert.alert('Success', 'Password updated successfully!');
        } catch (error) {
            console.error('Error updating password:', error);
            Alert.alert('Error', 'Failed to update password. Please try again later.');
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.label}>Current Password:</Text>
            <TextInput
                style={styles.input}
                secureTextEntry
                value={currentPassword}
                onChangeText={setCurrentPassword}
            />
            <Text style={styles.label}>New Password:</Text>
            <TextInput
                style={styles.input}
                secureTextEntry
                value={newPassword}
                onChangeText={setNewPassword}
            />
            <Text style={styles.label}>Confirm Password:</Text>
            <TextInput
                style={styles.input}
                secureTextEntry
                value={confirmPassword}
                onChangeText={setConfirmPassword}
            />
            
            <TouchableOpacity 
                style={[styles.button]} 
                onPress={handleChangePassword} 
            > 
                <Text style={styles.buttonText}>Change Password</Text> 
            </TouchableOpacity> 
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: 20,
    },
    label: {
        fontSize: 16,
        marginBottom: 5,
    },
    input: {
        width: '100%',
        height: 40,
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 5,
        marginBottom: 10,
        paddingHorizontal: 10,
    },
    button: { 
        backgroundColor: '#40E0D0',
        borderRadius: 8, 
        paddingVertical: 10, 
        alignItems: 'center', 
        marginTop: 16, 
        marginBottom: 12,
        width: '100%' 
    },
    buttonText: { 
        color: '#fff', 
        fontWeight: 'bold', 
        fontSize: 16, 
    },
});
