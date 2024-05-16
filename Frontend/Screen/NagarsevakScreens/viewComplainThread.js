import React, { useState, useEffect } from 'react';
import { ScrollView, View, Text, StyleSheet, map } from 'react-native';
import { Picker } from '@react-native-picker/picker'; 
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

/*const CommentCard = ({ userName, userDate, comment }) => {
  return (
    <View style={styles.commentCard}>
      <Text style={styles.userName}>{userName}</Text>
      <Text style={styles.userDate}>{userDate}</Text>
      <Text style={styles.commentText}>{comment}</Text>
    </View>
  );
};*/

export default function ViewComplainThread({ route }) {
  const { complaint } = route.params;
  const [status, setStatus] = useState(complaint.status);

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
          {/*<View style={styles.commentsContainer}>
            {complaint.comments.map((comment, index) => (
              <CommentCard
                key={index}
                userName={comment.userName}
                userDate={comment.userDate}
                comment={comment.comment}
              />
            ))}
          </View>*/}
        </View>
      )}
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
  userName: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  userDate: {
    fontSize: 12,
    color: '#666',
  },
  commentText: {
    fontSize: 14,
  },
});
