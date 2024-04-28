import React from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { MaterialIcons } from '@expo/vector-icons';
import CustomDrawerContent from './UserScreens/customDrawerContent';

import RegisterComplain from './UserScreens/registerComplain';
import ViewComplainThread from './/UserScreens/viewComplainThread';
import ChangePassword from './UserScreens/changePassword';
import SignOutScreen from './UserScreens/signOut';
import UserHomePage from './UserScreens/homePage';

const Drawer = createDrawerNavigator();
export default function UserProfile() {
    return(
        <Drawer.Navigator
        initialRouteName="UserHomePage"
        drawerContent={props => <CustomDrawerContent {...props} />}
        screenOptions={{
            drawerStyle: {
            backgroundColor: '#fff',  //to make background color of drawer to white
            width: 240,
            },
            headerStyle: {
            backgroundColor: '#40E0D0',
            },
            drawerContentStyle: {
            backgroundColor: '#fff', 
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
            fontWeight: 'bold',
            },
            headerTitleAlign: 'center',
        }}
        >
        <Drawer.Screen
        name="UserHomePage"
        component={UserHomePage}
        options={{
          title: 'Home',
          headerTitle: 'Home Page',
          drawerIcon: ({ size }) => (
            <MaterialIcons
              name="home"
              size={size}
              color="black"
            />
          ),
        }}
    />
        <Drawer.Screen
            name="RegisterComplain"
            component={RegisterComplain}
            options={{
            title: 'Register Complain',
            headerTitle: 'Register Complain',
            drawerIcon: ({ size }) => (
                <MaterialIcons
                name="report-problem"
                size={size}
                color="black"
                />
            ),
            }}
        />

        <Drawer.Screen
            name="ViewComplainThread"
            component={ViewComplainThread}
            options={{
            title: 'View Complain Thread',
            headerTitle: 'View Complain Thread',
            drawerIcon: ({ size }) => (
                <MaterialIcons
                name="chat"
                size={size}
                color="black"
                />
            ),
            }}
        />

        <Drawer.Screen
            name="ChangePassword"
            component={ChangePassword}
            options={{
            title: 'Change Password',
            headerTitle: 'Change Password',
            drawerIcon: ({ size }) => (
                <MaterialIcons
                name="vpn-key"
                size={size}
                color="black"
                />
            ),
            }}
        />

<Drawer.Screen
        name="Sign Out"
        component={SignOutScreen}
        options={{
            title: 'Sign Out',
            headerTitle: 'Sign Out',
            drawerIcon: ({ size }) => (
            <MaterialIcons
                name="logout"
                size={size}
                color="black"
            />
            ),
        }}
    />

    </Drawer.Navigator>
    );
}