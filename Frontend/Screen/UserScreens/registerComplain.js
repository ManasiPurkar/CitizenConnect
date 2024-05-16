import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, Alert } from 'react-native';
import { Picker } from '@react-native-picker/picker'; 
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function RegisterComplain() {
  const [area, setArea] = useState('');
  const [address, setAddress] = useState('');
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [department, setDepartment] = useState('');
  const [userId, setUserId] = useState(null);
  const [areas] = useState([
    { area_name: 'Central Bengaluru', area_code: '560001' },
    { area_name: 'Shivajinagar', area_code: '560002' },
    { area_name: 'Vasanth Nagar', area_code: '560003' },
    { area_name: 'Malleswaram', area_code: '560004' },
    { area_name: 'Rajajinagar', area_code: '560005' },
    { area_name: 'Sadashivanagar', area_code: '560006' },
    { area_name: 'Shantinagar', area_code: '560007' },
    { area_name: 'Basavanagudi', area_code: '560008' },
    { area_name: 'Jayanagar', area_code: '560009' },
    { area_name: 'Yeshwanthpur', area_code: '560010' }
  ]);
  const [departments] = useState([
    { department_id: 1, name: 'Electricity Department' },
    { department_id: 2, name: 'Cleaning Department' },
    { department_id: 3, name: 'Roads Department' },
    { department_id: 4, name: 'Water Department' }
  ]);

  useEffect(() => {
    fetchCitizenId();
  }, []);

  const fetchCitizenId = async () => {
    try {
      const citizenId = await AsyncStorage.getItem('userId');
      if (citizenId !== null) {
        setUserId(citizenId);
      }
    } catch (error) {
      console.error('Error fetching citizenID from AsyncStorage:', error);
    }
  };

  const handleSubmit = () => {
    const formData = {
      citizenId: userId,
      address: address,
      department_code: department,
      description: description,
      title: title,
      areaCode: area
    };

    axios.post('http://172.16.145.13:9093/citizen/register-complaint', formData)
      .then(response => {
        console.log('Complaint registered successfully:', response.data);
        setAddress('');
        setTitle('');
        setDescription('');
        setArea('');
        setDepartment('');
        showAlert(); // Call showAlert after successful registration
      })
      .catch(error => {
        console.error('Error registering complaint:', error);
      });
  };

  // Function to show alert message
  const showAlert = () => {
    Alert.alert(
      'Success',
      'Complaint registered successfully',
      [{ text: 'OK', onPress: () => console.log('OK Pressed') }],
      { cancelable: false }
    );
  };

  return (
    <View style={styles.container}>
      <Picker
        selectedValue={area}
        style={styles.dropdown}
        onValueChange={(itemValue, itemIndex) => setArea(itemValue)}
      >
        <Picker.Item label="Select Area" value="" />
        {areas.map(area => (
          <Picker.Item key={area.area_code} label={area.area_name} value={area.area_code} />
        ))}
      </Picker>

      <Picker
        selectedValue={department}
        style={styles.dropdown}
        onValueChange={(itemValue, itemIndex) => setDepartment(itemValue)}
      >
        <Picker.Item label="Select Department" value="" />
        {departments.map(dep => (
          <Picker.Item key={dep.department_id} label={dep.name} value={dep.department_id} />
        ))}
      </Picker>

      <TextInput
        style={styles.input}
        placeholder="Address (200 words)"
        multiline={true}
        numberOfLines={4}
        value={address}
        onChangeText={text => setAddress(text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Title"
        value={title}
        onChangeText={text => setTitle(text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Description (1 line only)"
        value={description}
        onChangeText={text => setDescription(text)}
      />
      
      <TouchableOpacity 
        style={[styles.button]}             
        onPress={handleSubmit} 
      > 
        <Text style={styles.buttonText}>Submit</Text> 
      </TouchableOpacity> 
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#fff',
  },
  dropdown: {
    width: '100%',
    marginBottom: 20,
    borderWidth: 1,
    borderColor: '#40E0D0',
    borderRadius: 6,
  },
  input: {
    width: '100%',
    padding: 10,
    marginBottom: 20,
    borderWidth: 1,
    borderColor: '#40E0D0',
    borderRadius: 6,
    fontSize: 18,
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
});
