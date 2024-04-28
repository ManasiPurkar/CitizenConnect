import React, { useState,useEffect } from 'react';
import { StyleSheet, Button, TextInput, Text, View, TouchableOpacity, ScrollView } from 'react-native';
import {useNavigation} from '@react-navigation/native';
import Header from './header';

export default function Register() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [cityCode, setCityCode] = useState('');
    const [areaCode, setAreaCode] = useState('');
    const [mobileNumber, setMobileNumber] = useState('');
    const [errors, setErrors] = useState({}); 
    const [isFormValid, setIsFormValid] = useState(false);

    const navigation = useNavigation();
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    useEffect(() => { 
        // Trigger form validation when any of the fields change
        validateForm(); 
    }, [firstName, lastName, email, password, confirmPassword, cityCode, areaCode, mobileNumber]); 

    const validateForm = () => { 
        let errors = {}; 
        if (!firstName) { 
            errors.name = 'First Name is required.'; 
        }
        if (!lastName) { 
            errors.name = 'Last Name is required.'; 
        }
        if (!email) { 
            errors.email = 'Email is required.'; 
        } else if (!/\S+@\S+\.\S+/.test(email)) { 
            errors.email = 'Email is invalid. Enter a valid email-id'; 
        } 
        if (!password) { 
            errors.password = 'Password is required.'; 
        } else if (!passwordRegex.test(password)) {
            errors.password = 'Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.';
        } 
        if (!confirmPassword) { 
            errors.confirmPassword = 'Confirm Password is required.'; 
        } else if (confirmPassword !== password) { 
            errors.confirmPassword = 'Password and Confirm Password do not match.'; 
        }
        
        if (!cityCode) { 
            errors.cityCode = 'City code is required.'; 
        }
        if (!areaCode) { 
            errors.areaCode = 'Area code is required.'; 
        }
        if (!mobileNumber) { 
            errors.mobileNumber = 'Mobile Number is required.'; 
        }
        // Set the errors and update form validity 
        setErrors(errors); 
        setIsFormValid(Object.keys(errors).length === 0); 
    };

    const handleSubmit = () => {
        // Navigate to the sign-up page
        if (isFormValid) { 
            // Form is valid, perform the submission logic 
            console.log('Form submitted successfully!'); 
            navigation.navigate('SignInPage');
        } else { 
            // Form is invalid, display error messages 
            console.log('Form has errors. Please correct them.'); 
        } 
    };
    
    return (
        <View style={styles.mainContainer}>
            <Header text="CitizenConnect"/>
            <ScrollView contentContainerStyle={styles.container} >
                {/* Header */}
                <View>
                    <Text style={styles.headerText}>REGISTRATION</Text>
                </View>

                {/* Input fields */}
                <Text style={styles.label}>First Name</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter First Name'
                    onChangeText={setFirstName}
                    value={firstName}
                    keyboardType='default'
                />

                <Text style={styles.label}>Last Name</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Last Name'
                    onChangeText={setLastName}
                    value={lastName}
                    keyboardType='default'
                />

                <Text style={styles.label}>Email</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Email'
                    onChangeText={setEmail}
                    value={email}
                    keyboardType='email-address'
                />

                <Text style={styles.label}>Password</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Password'
                    onChangeText={setPassword}
                    value={password}
                    secureTextEntry={true}
                    keyboardType='default'
                />

                <Text style={styles.label}>Confirm Password</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Confirm Password'
                    onChangeText={setConfirmPassword}
                    value={confirmPassword}
                    secureTextEntry={true}
                    keyboardType='default'
                />

                <Text style={styles.label}>Area Code</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Area Code'
                    onChangeText={setAreaCode}
                    value={areaCode}
                    keyboardType='numeric'
                />

                <Text style={styles.label}>City</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter City'
                    onChangeText={setCityCode}
                    value={cityCode}
                    keyboardType='default'
                />

                <Text style={styles.label}>Mobile Number</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Mobile Number'
                    onChangeText={setMobileNumber}
                    value={mobileNumber}
                    keyboardType='phone-pad'
                />
                
                {/*<View style={styles.buttonContainer}>
                    <Button title='Submit' color='#40E0D0' onPress={handleSubmit} />
    </View>*/}
                <TouchableOpacity 
                    style={[styles.button]} 
                    disabled={!isFormValid} 
                    onPress={handleSubmit} 
                > 
                    <Text style={styles.buttonText}>Submit</Text> 
                </TouchableOpacity> 
                {Object.values(errors).map((error, index) => ( 
                    <Text key={index} style={styles.error}> 
                        {error} 
                    </Text> 
                ))}
            </ScrollView>
        </View>
    );
}

const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        backgroundColor: '#fff',  
    },
    container: {
        padding: 20,
        flexGrow: 1,
        paddingVertical: 20,
        paddingHorizontal: 20,
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
        marginBottom: 20,
    },
    label: {
        fontSize: 18,
        marginBottom: 5,
        alignSelf: 'flex-start',
    },
    buttonContainer: {
        marginTop: 20,
    },
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
    error: { 
        color: 'red', 
        fontSize: 20, 
        marginBottom: 12, 
    },
});
