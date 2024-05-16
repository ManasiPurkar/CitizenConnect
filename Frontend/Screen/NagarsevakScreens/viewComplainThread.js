import React, { useState, useEffect } from 'react';
import { ScrollView, View, Text, StyleSheet, map, Modal } from 'react-native';
import { Picker } from '@react-native-picker/picker'; 
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

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

const handleAddComment = async () => {
  try {
    // Check if userId is available
    if (!userId) {
      throw new Error('User ID not found');
    }

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

    // Send a POST request to the API endpoint
    await axios.post(`http://172.16.145.13:9093/add/comment/${userId}`, requestBody);

    // Display success message
    Alert.alert('Comment added successfully');

    // Clear the user input
    setUserInput('');
  } catch (error) {
    console.error('Error adding comment:', error.message);
    // Display error message
    Alert.alert('Error', error.message);
  }
};



export default function ViewComplainThread({ route }) {
  const { complaint } = route.params;
  const [status, setStatus] = useState(complaint.status);
  const [userInput, setUserInput] = useState('');
  const [userId, setUserId] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [userRole, setUserRole] = useState('');

  useEffect(() => {
    // Fetch userId from AsyncStorage
    const fetchUserId = async () => {
      try {
        const storedUserId = await AsyncStorage.getItem('userId');
        const storedUserRole = await AsyncStorage.getItem('userRole');
        if (storedUserId !== null) {
          setUserId(storedUserId);
          setUserRole(storedUserRole);
        }
      } catch (error) {
        console.error('Error fetching userId from AsyncStorage:', error);
      }
    };

    fetchUserId();
  }, []);

  // Function to handle status change
  const handleChangeStatus = async (changedStatus) => {
      axios.put(`http://172.16.145.13:9093/complaint/change-status/${complaint.complaint_id}/${changedStatus}`)
        .then(response => {
          console.log('Status updated successfully:', response.data);
          const updatedStatus = response.data.status;
          setStatus(updatedStatus);
        })
        .catch(error => {
          console.error('Error updating status:', error);
        });
  };
  
  return (
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
          <Text style={styles.text}>Change Ticket Status: </Text>
          <Picker
            selectedValue={status}
            style={{ height: 50, width: 150 }}
            onValueChange={(itemValue, itemIndex) => handleChangeStatus(itemValue)}
          >  
            <Picker.Item label="Pending" value="pending" />
            <Picker.Item label="Ongoing" value="ongoing" />
            <Picker.Item label="Solved" value="solved" />
          </Picker>
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
        </View>
      )}
      <TouchableOpacity onPress={() => setModalVisible(true)}>
        <Text style={styles.commentButton}>Add Comment</Text>
      </TouchableOpacity>
      {/* Modal for adding comment */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <TextInput
              style={styles.input}
              placeholder="Enter your comment"
              value={userInput}
              onChangeText={text => setUserInput(text)}
            />
            <Button title="OK" onPress={() => {
              setModalVisible(false);
              handleAddComment();
            }} />
          </View>
        </View>
      </Modal>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  scrollView: {
    flexGrow: 1,
    backgroundColor: '#fff',
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
  commentText: {
    fontSize: 18,
    marginVertical: 10
  },
  commentButton: {
    color: 'blue', // Make the text blue
    textDecorationLine: 'underline', // Add underline effect
    margin: 5, // Add some space between "Don't have an account" and "Sign Up"
},
});
