import React, { useState, useEffect } from 'react';
import { ScrollView, View, Text, StyleSheet, map } from 'react-native';
import axios from 'axios';

const CommentCard = ({ userName, userDate, comment }) => {
  return (
    <View style={styles.commentCard}>
      <Text style={styles.userName}>{userName}</Text>
      <Text style={styles.userDate}>{userDate}</Text>
      <Text style={styles.commentText}>{comment}</Text>
    </View>
  );
};

export default function ViewComplainThread() {
  const [complaint, setComplaint] = useState(null);
  
  // Dummy complaint data for testing
  const dummyComplaint = {
    title: 'Water Leakage Issue',
    status: 'Open',
    date: '2024-05-13',
    vote: 10,
    department: 'Public Works Department',
    address: '123 Main Street, Cityville',
    description: 'There is a water leakage near the intersection of Main Street and Elm Street.',
    comments: [
      {
        userName: 'John Doe',
        userDate: '2024-05-12',
        comment: 'Thank you for reporting this issue. Our team will investigate.',
      },
      {
        userName: 'Jane Smith',
        userDate: '2024-05-11',
        comment: 'This is a serious problem. It needs to be fixed as soon as possible.',
      },
      // Add more dummy comments if needed
    ],
  };
  
  useEffect(() => {
    // Fetch complaint data when component mounts
    fetchData();
  }, []);

  const fetchData = () => {
    // Set the complaint data to the dummy complaint for testing
    setComplaint(dummyComplaint);
  };

  return (
    <ScrollView contentContainerStyle={styles.scrollView}>
      {complaint && (
        <View style={styles.complaintContainer}>
          <Text style={styles.title}>{complaint.title}</Text>
          <Text style={styles.subtitle}>Status: {complaint.status}</Text>
          <Text style={styles.text}>Date: {complaint.date}</Text>
          <Text style={styles.text}>Vote: {complaint.vote}</Text>
          <Text style={styles.text}>Department: {complaint.department}</Text>
          <Text style={styles.text}>Address: {complaint.address}</Text>
          <Text style={styles.text}>Description: {complaint.description}</Text>
          <View style={styles.commentsContainer}>
            {complaint.comments.map((comment, index) => (
              <CommentCard
                key={index}
                userName={comment.userName}
                userDate={comment.userDate}
                comment={comment.comment}
              />
            ))}
          </View>
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
