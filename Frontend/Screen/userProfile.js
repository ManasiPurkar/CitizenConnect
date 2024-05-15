import React, { useEffect, useState } from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { MaterialIcons } from '@expo/vector-icons';
import { AsyncStorage } from 'react-native';

// Importing Citizen Screens
import RegisterComplain from './UserScreens/registerComplain';
import ViewComplainThread_Citizen from './UserScreens/viewComplainThread';
import ChangePassword_Citizen from './UserScreens/changePassword';
import SignOutScreen_Citizen from './UserScreens/signOut';
import UserHomePage from './UserScreens/homePage';
import CustomDrawerContent_Citizen from './UserScreens/customDrawerContent';

// Importing Nagar-Sevak Screens
import NagarsevakHomePage_NS from './NagarsevakScreens/homePage';
import ViewComplainThread_NS from './NagarsevakScreens/viewComplainThread';
import ChangePassword_NS from './NagarsevakScreens/changePassword';
import SignOutScreen_NS from './NagarsevakScreens/signOut';
import CustomDrawerContent_NS from './NagarsevakScreens/customDrawerContent';

// Importing Admin Screens
import AddNagarsevak from './AdminScreens/addNagarsevak';
import SignOutScreen_Admin from './AdminScreens/signOut';

const Drawer = createDrawerNavigator();

export default function UserProfile() {
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    const fetchUserRole = async () => {
      try {
        const role = await AsyncStorage.getItem('userRole');
        setUserRole(role);
      } catch (error) {
        console.error('Error fetching user role:', error);
      }
    };
    fetchUserRole();
  }, []);

  if (userRole === null) {
    return null; // Handle loading state
  }

  return (
    <Drawer.Navigator
      initialRouteName={
        userRole === 'ROLE_ADMIN' ? 'Admin' : userRole === 'ROLE_CITIZEN' ? 'Citizen' : 'Nagarsevak'
      }
      drawerContent={props =>
        userRole === 'ROLE_ADMIN' ? (
          <CustomDrawerContent_Admin {...props} />
        ) : userRole === 'ROLE_CITIZEN' ? (
          <CustomDrawerContent_Citizen {...props} />
        ) : (
          <CustomDrawerContent_NS {...props} />
        )
      }
      screenOptions={{
        drawerStyle: {
          backgroundColor: '#fff', //to make background color of drawer to white
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
      }}>
      {userRole === 'ROLE_ADMIN' && (
        <>
          <Drawer.Screen
            name="Admin"
            component={AddNagarsevak}
            options={{
              title: 'Add Nagarsevak',
              headerTitle: 'Add Nagarsevak',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="person-add" size={size} color="black" />
              ),
            }}
          />
          <Drawer.Screen
            name="SignOut_Admin"
            component={SignOutScreen_Admin}
            options={{
              title: 'Sign Out',
              headerTitle: 'Sign Out',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="logout" size={size} color="black" />
              ),
            }}
          />
        </>
      )}
      {userRole === 'ROLE_CITIZEN' && (
        <>
          <Drawer.Screen
            name="UserHomePage"
            component={UserHomePage}
            options={{
              title: 'Home',
              headerTitle: 'Home Page',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="home" size={size} color="black" />
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
                <MaterialIcons name="report-problem" size={size} color="black" />
              ),
            }}
          />
          <Drawer.Screen
            name="ViewComplainThread"
            component={ViewComplainThread_Citizen}
            options={{
              title: 'View Complain Thread',
              headerTitle: 'View Complain Thread',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="chat" size={size} color="black" />
              ),
            }}
          />
          <Drawer.Screen
            name="ChangePassword"
            component={ChangePassword_Citizen}
            options={{
              title: 'Change Password',
              headerTitle: 'Change Password',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="vpn-key" size={size} color="black" />
              ),
            }}
          />
          <Drawer.Screen
            name="SignOut_Citizen"
            component={SignOutScreen_Citizen}
            options={{
              title: 'Sign Out',
              headerTitle: 'Sign Out',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="logout" size={size} color="black" />
              ),
            }}
          />
        </>
      )}
      {userRole === 'ROLE_NAGARSEVAK' && (
        <>
          <Drawer.Screen
            name="NagarsevakHomePage"
            component={NagarsevakHomePage_NS}
            options={{
              title: 'Nagarsevak Home',
              headerTitle: 'Nagarsevak Home Page',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="home" size={size} color="black" />
              ),
            }}
          />
          <Drawer.Screen
            name="ViewComplainThread_NS"
            component={ViewComplainThread_NS}
            options={{
              title: 'View Complain Thread',
              headerTitle: 'View Complain Thread',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="chat" size={size} color="black" />
              ),
            }}
          />
          <Drawer.Screen
            name="ChangePassword_NS"
            component={ChangePassword_NS}
            options={{
              title: 'Change Password',
              headerTitle: 'Change Password',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="vpn-key" size={size} color="black" />
              ),
            }}
          />
          <Drawer.Screen
            name="SignOut_NS"
            component={SignOutScreen_NS}
            options={{
              title: 'Sign Out',
              headerTitle: 'Sign Out',
              drawerIcon: ({ size }) => (
                <MaterialIcons name="logout" size={size} color="black" />
              ),
            }}
          />
        </>
      )}
    </Drawer.Navigator>
  );
}
