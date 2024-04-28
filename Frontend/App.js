import 'react-native-gesture-handler';
import React from 'react';
import { StyleSheet } from 'react-native';
import {NavigationContainer} from "@react-navigation/native";
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SignIn from './Screen/signIn';
import Register from './Screen/register';
import ForgotPassword from './Screen/forgotPassword';
import ResetPassword from './Screen/resetPassword';

//import HomePage from './Screen/UserScreens/homePage';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="UserHomePage" screenOptions={{
        headerShown: false, // Hide the header for all screens in this navigator
      }}>
      <Stack.Screen name="SignInPage" component={SignIn} />
      <Stack.Screen name="RegisterPage" component={Register} />
      <Stack.Screen name="ForgotPasswordPage" component={ForgotPassword} />
      <Stack.Screen name="ResetPasswordPage" component={ResetPassword} />
      {/*<Stack.Screen name="UserHomePage" component={HomePage} /> */}
      </Stack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
