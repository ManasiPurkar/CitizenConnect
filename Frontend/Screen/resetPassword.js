import React, { useState } from 'react';
import { View, Text, TextInput, StyleSheet, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import Header from './header';

export default function ResetPassword() {
    // const navigation = useNavigation();
    // const [password, setPassword] = useState('');
    // const [confirmPassword, setConfirmPassword] = useState('');
    // const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;

    // const handleResetPassword = () => {
    //     if (password.trim() === '' || confirmPassword.trim() === '') {
    //         showToast('Please enter both passwords.');
    //         return;
    //     }

    //     if (password !== confirmPassword) {
    //         showToast('Passwords do not match.');
    //         return;
    //     }

    //     if (!passwordRegex.test(password)) {
    //         showToast('Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 6 characters long.');
    //         return;
    //     }

    //     // Reset password logic goes here
    //     showToast('Password reset successfully.');
        
    //     navigation.navigate('SignUpPage');
    // };

    // const showToast = (message) => {
    //     ToastAndroid.showWithGravity(
    //         message,
    //         ToastAndroid.SHORT,
    //         ToastAndroid.BOTTOM
    //     );
    // };

    // return (
    //     <View style={styles.mainContainer}>
    //     <Header text="CitizenConnect"/>
    //     <View style={styles.Container}>
    //         <View>
    //             <Text style={styles.headerText}>RESET PASSWORD</Text>
    //         </View>
    //         <Text style={styles.label}>Enter new Password</Text>
    //         <TextInput
    //             placeholder="Password"
    //             value={password}
    //             onChangeText={setPassword}
    //             secureTextEntry={true}
    //             autoCapitalize="none"
    //             autoCorrect={false}
    //             style={styles.input}
    //         />
    //         <Text style={styles.label}>Confirm Password</Text>
    //         <TextInput
    //             placeholder="Confirm Password"
    //             value={confirmPassword}
    //             onChangeText={setConfirmPassword}
    //             secureTextEntry={true}
    //             autoCapitalize="none"
    //             autoCorrect={false}
    //             style={styles.input}
    //         />
    //         <TouchableOpacity 
    //                 style={[styles.button]} 
    //                 onPress={handleResetPassword} 
    //         > 
    //             <Text style={styles.buttonText}>Submit</Text> 
    //         </TouchableOpacity> 
    //     </View>
    //     </View>
    // );
}
const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        backgroundColor: '#fff',  
    },
    container: {
        flexGrow: 1,
        padding: 20,
        width: '80%',
    },
    label: {
        fontSize: 18,
        marginBottom: 5,
        alignSelf: 'flex-start',
        marginHorizontal: 20,
    },
    button: { 
        backgroundColor: '#40E0D0',
        borderRadius: 8, 
        paddingVertical: 10, 
        alignItems: 'center', 
        margin: 20,
    },
    input: {
        borderWidth: 1,
        borderColor: '#40E0D0',
        padding: 10,
        fontSize: 18,
        borderRadius: 6,
        margin: 20,
    },
    headerText: {
        alignItems: 'center',
        marginVertical: 20,
        textAlign: 'center',
        fontSize: 15,
        fontWeight: 'bold'
    },
})