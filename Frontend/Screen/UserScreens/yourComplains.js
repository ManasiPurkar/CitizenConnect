import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, ScrollView } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';
import {useNavigation} from '@react-navigation/native';
//import axios from 'axios';

export default function YourComplains(){
    const navigation = useNavigation();

    //Testing purpose
    const complaints = [
        {
            description: "This is a complaint description",
            date: "April 30, 2024",
            time: "10:30 AM",
            department: "Department Name",
            votes: 10
        },
        {
            description: "This is a complaint description",
            date: "April 30, 2024",
            time: "10:30 AM",
            department: "Department Name",
            votes: 10
        },
        {
            description: "This is a complaint description",
            date: "April 30, 2024",
            time: "10:30 AM",
            department: "Department Name",
            votes: 10
        },
        {
            description: "This is a complaint description",
            date: "April 30, 2024",
            time: "10:30 AM",
            department: "Department Name",
            votes: 10
        },
        {
            description: "This is a complaint description",
            date: "April 30, 2024",
            time: "10:30 AM",
            department: "Department Name",
            votes: 10
        }

    ];
     

    /*const [complaints, setComplaints] = useState([]);

    useEffect(() => {
        // Fetch complaints from the backend API
        axios.get('your_backend_api_url')
            .then(response => {
                setComplaints(response.data); // Assuming response.data is an array of complaint objects
            })
            .catch(error => {
                console.error('Error fetching complaints:', error);
            });
    }, []);*/

    const handleViewComplainThread = () => {
        navigation.navigate('ViewComplainThread');
    }

    return(
        <ScrollView contentContainerStyle={styles.scrollView}>
            {complaints.map((complaint, index) => (
            <View key={index} style={styles.cardContainer}>
                {/* First Part: Complaint Details */}
                <View style={styles.complaintDetails}>
                    <Text style={styles.description}>{complaint.description}</Text>
                    <View style={styles.dateTimeContainer}>
                        <Text style={styles.dateTimeText}>DATE : {complaint.date}</Text>
                        <Text style={styles.dateTimeText}>TIME : {complaint.time}</Text>
                    </View>
                    <Text style={styles.department}>DEPARTMENT : {complaint.department}</Text>
                </View>

                {/* Second Part: Voting */}
                <View style={styles.voting}>
                    <View style={styles.iconAndVote}>
                        <TouchableOpacity style={styles.voteButton}>
                            <MaterialIcons name="thumb-up" size={24} color="blue" />
                        </TouchableOpacity>
                        <Text style={styles.voteCount}>{complaint.votes}</Text>
                    </View>
                    {/* Navigate to complain thread upon clicking */}
                    <TouchableOpacity onPress={handleViewComplainThread}>
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
        justifyContent: 'center',
    },
    cardContainer: {
        width: '90%',
        //marginVertical: 10,
        marginTop: 20,
        backgroundColor: '#e3f1f7',
        padding: 20,
        borderRadius: 10,
        elevation: 3, // for Android
    },
    complaintDetails: {
        marginBottom: 10,
    },
    description: {
        fontSize: 16,
        fontWeight: 'bold',
        marginBottom: 10,
    },
    dateTimeContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginBottom: 5,
    },
    dateTimeText: {
        fontSize: 14,
    },
    department: {
        fontSize: 14,
    },
    voting: {
        flexDirection: 'row',
        //alignItems: 'center',
        justifyContent: 'space-between',
    },
    voteButton: {
        marginRight: 10,
    },
    voteCount: {
        fontSize: 16,
    },
    iconAndVote: {
        flexDirection: 'row',
    },
    viewComplain: {
        color: 'blue', // Make the text blue
        textDecorationLine: 'underline', // Add underline effect
    },
});
