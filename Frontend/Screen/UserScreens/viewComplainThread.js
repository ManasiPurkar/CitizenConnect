import React, { useState, useEffect } from 'react';
import { ScrollView, View, Text, StyleSheet, map, Modal, TouchableOpacity, TextInput, Button, Alert } from 'react-native';
import { Picker } from '@react-native-picker/picker'; 
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import Header from '../header';
import { BASE_URL } from '../../constants';

const CommentCard = ({ userName, userRole, comment, userDate, userTime }) => {
  return (
    <View style={styles.commentCard}>
      <View style={styles.userInfoContainer}>
        <Text style={styles.userName}>{userName}</Text>
        <Text style={styles.userRole}>{userRole}</Text>
      </View>
      <Text style={styles.commentText}>{comment}</Text>
      <View style={styles.timestampContainer}>
        <Text style={styles.timestamp}>{userDate}</Text>
        <Text style={[styles.timestamp, styles.time]}>{userTime}</Text>
      </View>
    </View>
  );
};


export default function ViewComplainThread({ route }) {
  const { complaint } = route.params;
  const [userInput, setUserInput] = useState('');
  const [userId, setUserId] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [userRole, setUserRole] = useState('');
  const [jwtToken, setJwtToken] = useState('');

  useEffect(() => {
    // Fetch userId and token from AsyncStorage
    const fetchUserId = async () => {
      try {
        const storedUserId = await AsyncStorage.getItem('userId');
        const storedUserRole = await AsyncStorage.getItem('userRole');
        const token = await AsyncStorage.getItem('accessToken');

        if (storedUserId !== null) {
          setUserId(storedUserId);
          setUserRole(storedUserRole);
          setJwtToken(token);
        }
      } catch (error) {
        console.error('Error fetching userId from AsyncStorage:', error);
      }
    };
    fetchUserId();
  }, []);

  
  const handleAddComment = async (userId, userInput, userRole) => {
    try {
      // Check if userId is available
      if (!userId) {
        throw new Error('User ID not found');
      }
      //console.log(userId);
      // Check if user input is empty
      if (!userInput.trim()) {
        throw new Error('Please enter a comment');
      }
  
      // Prepare the request body
      const requestBody = {
        userRole: userRole,
        complaintId: complaint.complaint_id,
        comment: userInput.trim()
      };
      console.log(requestBody);
      // Send a POST request to the API endpoint
      await axios.post(`${BASE_URL}/add/comment/${userId}`, requestBody, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${jwtToken}`,
        },
      });
  
      // Display success message
      Alert.alert('Comment added successfully');
  
      // Clear the user input
      setUserInput('');
    } catch (error) {
      console.error('Error adding comment:', error.message);
      console.log(error.response.data.message);
      // Display error message
      Alert.alert('Error', error.message);
    }
  };
    
  return (
    <View style={styles.container}>
      <Header text="Complain Thread"/>
      <ScrollView contentContainerStyle={styles.scrollView}>
        {complaint && (
          <View style={styles.complaintContainer}>
            <Text style={styles.title}>{complaint.title}</Text>
            <Text style={styles.subtitle}>Status: {complaint.status}</Text>
            <Text style={styles.text}>Date: {complaint.date}</Text>
            <Text style={styles.text}>Time: {complaint.eventTime}</Text>
            <Text style={styles.text}>Department: {complaint.department.department_name}</Text>
            <Text style={styles.text}>Address: {complaint.address}</Text>
            <Text style={styles.text}>Description: {complaint.description}</Text>
            
            <View style={styles.commentsContainer}>
              {complaint.comments?.map((comment, index) => (   //To display comment only if it exists
                <CommentCard
                  key={index}
                  userName={comment.userName}
                  userDate={comment.eventDate} 
                  userTime={comment.eventTime}
                  comment={comment.comment}
                  userRole={comment.userRole}
                />
              ))}
            </View>
            <TouchableOpacity style={[styles.button]} onPress={() => setModalVisible(true)}>
              <Text style={styles.buttonText}>Add Comment</Text>
            </TouchableOpacity>
          </View>
        )}
        {/* Modal for adding comment */}
        <Modal
          animationType="slide"
          transparent={true}
          visible={modalVisible}
          onRequestClose={() => setModalVisible(false)}
        >
          <View style={styles.centeredView}>
            <View style={styles.modalView}>
            <Text style={styles.heading}>Enter the comment</Text>
              <TextInput
                style={styles.tagInput}
                value={userInput}
                onChangeText={text => setUserInput(text)}
              />
              <Button title="OK" onPress={() => {
                setModalVisible(false);
                handleAddComment(userId, userInput, userRole);
              }} />
            </View>
          </View>
        </Modal>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
  },
  scrollView: {
    flexGrow: 1,
    alignItems: 'center',
  },
  complaintContainer: {
    width: '90%',
    marginTop: 20,
    backgroundColor: '#e3f1f7',
    padding: 20,
    borderRadius: 10,
    elevation: 3, // for Android
  },
  title: {
    fontSize: 19,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  subtitle: {
    fontSize: 15,
    marginBottom: 10,
  },
  text: {
    fontSize: 15,
    marginBottom: 10,
  },
  commentsContainer: {
    marginTop: 20,
  },
  commentCard: {
    marginBottom: 10,
    backgroundColor: '#fff',
    padding: 10,
    borderRadius: 10,
    elevation: 3, // for Android
  },
  userInfoContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  timestampContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 5,
    fontSize: 16,
  },
  userName: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  userDate: {
    fontSize: 12,
  },
  button: { 
    backgroundColor: '#40E0D0',
    borderRadius: 8, 
    padding: 10, 
    alignItems: 'center', 
    marginTop: 16, 
    marginBottom: 12, 
  },
  buttonText: { 
      color: '#fff', 
      fontWeight: 'bold', 
      fontSize: 16, 
  }, 
  centeredView: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 22,
  },
  modalView: {
    width: '100%',
    backgroundColor: "white",
    borderRadius: 20,
    padding: 35,
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5
  },
  tagInput: {
    width: '80%',
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    marginBottom: 20,
    paddingLeft: 10
  },
  heading: {
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: 'center'
  },
});