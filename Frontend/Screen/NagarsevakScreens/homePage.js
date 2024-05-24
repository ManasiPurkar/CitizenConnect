import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, ScrollView } from 'react-native';
import { useNavigation, useIsFocused } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import { BASE_URL } from '../../constants';

export default function NagarsevakHomePage() {
    const navigation = useNavigation();
    const isFocused = useIsFocused();
    const [complaints, setComplaints] = useState([]);
    const [userId, setUserId] = useState(null);
    const [accessToken, setAccessToken] = useState(null);

    useEffect(() => {
        // Fetch userId and token from AsyncStorage
        const fetchUserId = async () => {
            try {
                const storedUserId = await AsyncStorage.getItem('userId');
                const storedAccessToken = await AsyncStorage.getItem('accessToken');
                if (storedUserId !== null && storedAccessToken !== null) {
                    setUserId(storedUserId);
                    setAccessToken(storedAccessToken);
                }
            } catch (error) {
                console.error('Error fetching userId from AsyncStorage:', error);
            }
        };
        fetchUserId();
    }, []);

    useEffect(() => {
        // Fetch complaints only if the screen is focused and userId is available
        if (isFocused && userId  && accessToken) {
            axios.get(`${BASE_URL}/nagarsevak/complaints/${userId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            .then(response => {
                setComplaints(response.data);
            })
            .catch(error => {
                console.error('Error fetching complaints:', error);
            });
            //console.log(complaints);
        }
    }, [isFocused, userId, accessToken]); // Adding userId as a dependency

    const handleViewComplainThread = (complaint) => {
        // Pass the complaint details as a parameter to the navigation function
        navigation.navigate('ViewComplainThread_NS', { complaint });
    };

    return (
        <ScrollView contentContainerStyle={styles.scrollView}>
            {complaints.map(complaint => (
                <View key={complaint.complaint_id} style={styles.cardContainer}>
                    {/* Title */}
                    <Text style={styles.title}>{complaint.title}</Text>

                    {/* Date and Time */}
                    <View style={styles.dateTimeContainer}>
                        <Text style={styles.dateTimeDate}>Date: {complaint.date}</Text>
                        <Text style={styles.dateTimeTime}>Time: {complaint.eventTime}</Text>
                    </View>

                    {/* Description 
                    <Text style={styles.description}>Description: {complaint.description}</Text>*/}

                    {/* Address */}
                    <Text style={styles.info}>Address: {complaint.address}</Text>

                    {/* Status */}
                    <Text style={styles.info}>Status: {complaint.status}</Text>

                    {/* Department */}
                    <Text style={styles.info}>Department: {complaint.department.department_name}</Text>

                    {/* Second Part: Voting */}
                    <View style={styles.voting}>
                        {/* Navigate to complain thread upon clicking */}
                        <TouchableOpacity onPress={() => handleViewComplainThread(complaint)}>
                            <Text style={styles.viewComplain}>View Complain Thread</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            ))}
        </ScrollView>
    );
}

const styles = StyleSheet.create({
    scrollView: {
        flexGrow: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
    },
    cardContainer: {
        width: '90%',
        marginTop: 20,
        backgroundColor: '#e3f1f7',
        padding: 20,
        borderRadius: 10,
        elevation: 3, // for Android
    },
    title: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 10,
        textAlign: 'center',
    },
    dateTimeContainer: {
        flexDirection: 'row', // Arrange date and time horizontally
        marginBottom: 10,
    },
    dateTimeDate: {
        fontSize: 14,
        marginRight: 80, // Add some spacing between date and time
    },
    dateTimeTime: {
        fontSize: 14,
    },
    description: {
        fontSize: 14,
        marginBottom: 10,
    },
    info: {
        fontSize: 14,
        marginBottom: 10,
    },
    viewComplain: {
        color: 'blue', // Make the text blue
        textDecorationLine: 'underline', // Add underline effect
        textAlign: 'right'
    },
});
