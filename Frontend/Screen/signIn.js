import React, { useState, useEffect } from 'react';
import { StyleSheet, Alert, TextInput, Text, View, TouchableOpacity } from 'react-native';
import {useNavigation} from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Header from './header';
import UserProfile from './userProfile';
import { BASE_URL } from '../constants';

export default function SignIn() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({}); 
    const [isFormValid, setIsFormValid] = useState(false); 

    const navigation = useNavigation();
    //const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;

    useEffect(() => { 
        // Trigger form validation when email, or password changes 
        validateForm(); 
    }, [username, password]); 

    const validateForm = () => { 
        let errors = {}; 
        // Validate email field 
        if (!username) { 
            errors.username = 'Username is required.'; 
        } else if (!/\S+@\S+\.\S+/.test(username)) {    //username must a valid email
            errors.username = 'Username must be at least 6 characters.'; 
        } 
        // Validate password field 
        if (!password) { 
            errors.password = 'Password is required.'; 
        } 
        // Set the errors and update form validity 
        setErrors(errors); 
        setIsFormValid(Object.keys(errors).length === 0); 
    };

    const handleSubmit = async () => {
        if (isFormValid) { 
            console.log(username);
            console.log(password);
            try {
                const response = await fetch(`${BASE_URL}/v1/auth/login`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        username: username,
                        password: password
                    })
                });

                const data = await response.json();
                console.log(data);

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || 'An error occurred');
                }
            
                if (response.ok) {
                    // Login successful
                    await AsyncStorage.setItem('accessToken', data.token);
                    await AsyncStorage.setItem('userEmail', data.email);
                    await AsyncStorage.setItem('userId', data.user_id.toString());
                    await AsyncStorage.setItem('userRole', data.role);
                    await AsyncStorage.setItem('userName', data.name);

                    // Redirect to the appropriate screen based on role
                    navigation.navigate(UserProfile);
                }
            } catch (error) {
                console.error('Error logging in:', error.message);
                Alert.alert('Login Failed', error.message);
            }
        } else { 
            console.log('Form has errors. Please correct them.'); 
        } 
    };


    const handleSignUpPress = () => {
        // Navigate to the Registration page
        navigation.navigate('RegisterPage');
    };
    const handleForgotPasswordPress = () => {
        navigation.navigate('ForgotPasswordPage');
    };
    return (
        <View>
            <Header text="CitizenConnect"/>
            <View style={styles.container}>
                {/* Header */}
                <View>
                    <Text style={styles.headerText}>SIGN IN</Text>
                </View>

                {/* Input fields */}
                <Text style={styles.label}> Enter Username: </Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Username'
                    onChangeText={setUsername}
                    value={username}
                />
                <Text style={styles.label}> Enter Password: </Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Password'
                    onChangeText={setPassword}
                    value={password}
                    secureTextEntry={true}
                />

                {/*<View style={styles.forgotPassword}>
                    <TouchableOpacity onPress={handleForgotPasswordPress}>
                        <Text style={styles.forgotPasswordText}>Forgot Password</Text>
                    </TouchableOpacity>
                </View>
    */}

                {/*<Button title='Submit' color='#40E0D0' onPress={handleSubmit} />*/}
                <TouchableOpacity 
                    style={[styles.button]} 
                    disabled={!isFormValid} 
                    onPress={handleSubmit} 
                > 
                    <Text style={styles.buttonText}>Submit</Text> 
                </TouchableOpacity> 

                <View style={styles.line}></View>

                <View style={styles.singleLine}>
                    <Text>Don't have an account </Text>
                    <TouchableOpacity onPress={handleSignUpPress}>
                        <Text style={styles.signUpText}>Sign Up</Text>
                    </TouchableOpacity>
                </View>
                {Object.values(errors).map((error, index) => ( 
                    <Text key={index} style={styles.error}> 
                        {error} 
                    </Text> 
                ))} 
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        padding: 20, // Added horizontal padding
        width: '100%', // Set the width to 80% of the parent container
    },
    headerText: {
        alignItems: 'center',
        marginBottom: 20,
        textAlign: 'center',
        fontSize: 15,
        fontWeight: 'bold'
    },
    input: {
        borderWidth: 1,
        borderColor: '#40E0D0',
        padding: 10,
        fontSize: 18,
        borderRadius: 6,
        marginBottom: 20, // Increase bottom margin
        width: '100%', // Set input width to 100% of the container
    },
    label: {
        fontSize: 18,
        marginBottom: 5,
        alignSelf: 'flex-start', // Align labels to the left
    },
    line: {
        marginTop: 20,
        width: '100%', // or specify a specific width
        height: 3, // adjust height as needed
        backgroundColor: 'rgba(0, 0, 0, 0.3)', // faint color with opacity
    },
    signUpText: {
        color: 'blue', // Make the text blue
        textDecorationLine: 'underline', // Add underline effect
        margin: 5, // Add some space between "Don't have an account" and "Sign Up"
    },
    singleLine:{
        flexDirection: 'row', // Align children horizontally
        alignItems: 'center', // Center items vertically
    },
    forgotPassword: {
        marginLeft: 'auto', // Move the component to the right end
        marginRight: 0, // Add some right margin for spacing from screen edge
        alignSelf: 'flex-start', // Align the component to the start (right end in LTR languages)
        marginBottom: 20,
    },
    forgotPasswordText: {
        color: 'blue', // Make the text blue
        textDecorationLine: 'underline', // Add underline effect
    },
    button: { 
        backgroundColor: '#40E0D0',
        borderRadius: 8, 
        paddingVertical: 10, 
        alignItems: 'center', 
        marginTop: 10, 
        marginBottom: 10, 
    },
    buttonText: { 
        color: '#fff', 
        fontWeight: 'bold', 
        fontSize: 16, 
    }, 
    error: { 
        color: 'red', 
        fontSize: 20, 
        marginBottom: 12, 
    },
});
