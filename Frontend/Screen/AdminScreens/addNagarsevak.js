import React, { useState, useEffect } from 'react';
import { StyleSheet, TextInput, Text, View, TouchableOpacity, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Picker } from '@react-native-picker/picker';
import { Alert } from 'react-native';
import axios from 'axios';

export default function AddNagarsevak() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [areaCode, setAreaCode] = useState('');
    const [selectedArea, setSelectedArea] = useState('');
    const [mobileNumber, setMobileNumber] = useState('');
    const [errors, setErrors] = useState({});
    const [isFormValid, setIsFormValid] = useState(false);
    const [areas] = useState([
        { area_name: 'Central Bengaluru', area_code: '560001' },
        { area_name: 'Shivajinagar', area_code: '560002' },
        { area_name: 'Vasanth Nagar', area_code: '560003' },
        { area_name: 'Malleswaram', area_code: '560004' },
        { area_name: 'Rajajinagar', area_code: '560005' },
        { area_name: 'Sadashivanagar', area_code: '560006' },
        { area_name: 'Shantinagar', area_code: '560007' },
        { area_name: 'Basavanagudi', area_code: '560008' },
        { area_name: 'Jayanagar', area_code: '560009' },
        { area_name: 'Yeshwanthpur', area_code: '560010' }
    ]); // Predefined list of areas

    const navigation = useNavigation();

    useEffect(() => {
        validateForm();
    }, [firstName, lastName, email, selectedArea, mobileNumber]);

    const validateForm = () => {
        let errors = {};
        if (!firstName) {
            errors.firstname = 'First Name is required.';
        }
        if (!lastName) {
            errors.lastname = 'Last Name is required.';
        }
        if (!email) {
            errors.email = 'Email is required.';
        } else if (!/\S+@\S+\.\S+/.test(email)) {
            errors.email = 'Email is invalid. Enter a valid email-id';
        }
        if (!selectedArea) {
            errors.selectedArea = 'Please select an area.';
        }
        if (!mobileNumber) {
            errors.mobileNumber = 'Mobile Number is required.';
        }
        setErrors(errors);
        setIsFormValid(Object.keys(errors).length === 0);
    };

    const handleSubmit = () => {
        if (isFormValid) {
            // Find the selected area object from areas array
            const selectedAreaObject = areas.find(area => area.area_name === selectedArea);
            if (selectedAreaObject) {
                // If area is found, set its area_code and make the POST request
                setAreaCode(selectedAreaObject.area_code);
                axios.post('http://172.16.145.13:9093/register/Nagarsevak', {
                    firstname: firstName,
                    lastname: lastName,
                    mobile_no: mobileNumber,
                    areaCode: selectedAreaObject.area_code,
                    email: email
                })
                    .then(response => {
                        console.log('Registration successful:', response.data);
                        Alert.alert(
                            'Success',
                            'Nagarsevak added successfully!!',
                            [
                                { text: 'OK', onPress: () => console.log('OK Pressed') }
                            ],
                            { cancelable: false }
                        );
                    })
                    .catch(error => {
                        console.error('Registration failed:', error);
                    });
            } else {
                console.error('Selected area not found.');
            }
        } else {
            console.log('Form has errors. Please correct them.');
        }
    };

    return (
        <View style={styles.mainContainer}>
            <ScrollView contentContainerStyle={styles.container} >

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

                <Text style={styles.label}>Area</Text>
                <Picker
                    selectedValue={selectedArea}
                    onValueChange={(itemValue, itemIndex) => setSelectedArea(itemValue)}
                >
                    <Picker.Item label="Select an area..." value="" />
                    {areas.map(area => (
                        <Picker.Item key={area.area_code} label={area.area_name} value={area.area_name} />
                    ))}
                </Picker>

                <Text style={styles.label}>Mobile Number</Text>
                <TextInput
                    style={styles.input}
                    placeholder='Enter Mobile Number'
                    onChangeText={setMobileNumber}
                    value={mobileNumber}
                    keyboardType='phone-pad'
                />

                <TouchableOpacity
                    style={[styles.button]}
                    disabled={!isFormValid}
                    onPress={handleSubmit}
                >
                    <Text style={styles.buttonText}>Register</Text>
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
