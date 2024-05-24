import React from 'react';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import YourComplains from './yourComplains';
import YourArea from './yourArea';

const Tab = createMaterialTopTabNavigator();

export default function UserHomePage(){
    return(
        <Tab.Navigator 
            initialRouteName='YourComplains' 
            screenOptions={{
                tabBarLabelStyle: { fontSize: 12, width: '50%', textAlign: 'center' },
                tabBarStyle: { backgroundColor: 'powderblue' },
            }}
        >
            <Tab.Screen name="Your Complains" component={YourComplains} />
            <Tab.Screen name="Complains in your Area" component={YourArea} />
        </Tab.Navigator>
    );
}