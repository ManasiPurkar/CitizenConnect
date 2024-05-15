import React, { useState,useEffect } from 'react';
import { StyleSheet, Button, TextInput, Text, View, TouchableOpacity, ScrollView } from 'react-native';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';

const Tab = createMaterialTopTabNavigator();

export default function NagarsevakHomePage(){
    return(
        <Text>Home Page of Nagarsevak</Text>
    );
}