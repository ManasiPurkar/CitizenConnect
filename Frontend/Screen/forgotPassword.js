import React, { useState } from 'react';
import { View, Text, TextInput, Button, ToastAndroid, StyleSheet, TouchableOpacity } from 'react-native';
import { useNavigation } from "@react-navigation/native";
import Header from './header';
// import axios from 'axios'; // Import axios for making HTTP requests

export default function ForgotPassword() {
    const [email, setEmail] = useState('');
    const navigation = useNavigation(); 

    const sendPasswordResetEmail = async (email) => {
        try {
            // const response = await axios.post('https://example.com/reset-password', { email });
            // return response.data;
        } catch (error) {
            throw new Error('Failed to send password reset email.');
        }
    };

    const handleResetPassword = async () => {
        if (email.trim() === '') {
            showToast('Please enter your email address.');
            return;
        }

        try {
            await sendPasswordResetEmail(email);
            showToast('Password reset email sent. Please check your inbox.');
            navigation.navigate('ResetPasswordPage');
        } catch (error) {
            showToast(error.message);
        }
    };

    const showToast = (message) => {
        ToastAndroid.showWithGravity(
            message,
            ToastAndroid.SHORT,
            ToastAndroid.BOTTOM
        );
    };

    return (
        <View style={{ flex: 1 }}>
            <Header text="CITIZENCONNECT" />
            <View style={{ flex: 1, justifyContent: 'flex-start', paddingTop: 20, paddingHorizontal: 20 }}>
                <Text style={{ fontSize: 24, fontWeight: 'bold' }}>Enter your Email Id :</Text>
                <TextInput
                    style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginTop: 10 }}
                    placeholder="   Email"
                    value={email}
                    onChangeText={setEmail}
                    keyboardType="email-address"
                    autoCapitalize="none"
                    autoCorrect={false}
                />
                <TouchableOpacity 
                    style={[styles.button]} 
                    onPress={handleResetPassword} 
                > 
                    <Text style={styles.buttonText}>Submit</Text> 
                </TouchableOpacity> 
            </View>
        </View>
    );
}

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