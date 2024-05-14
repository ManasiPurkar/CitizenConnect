import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, Image, Platform } from 'react-native';
//import ImagePicker from 'react-native-image-picker'; // Import the image picker
import { Picker } from '@react-native-picker/picker'; 
import axios from 'axios';

export default function RegisterComplain() {
  const [area, setArea] = useState('');
  const [areas, setAreas] = useState([]);
  const [address, setAddress] = useState('');
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  //const [photo, setPhoto] = useState(null); // Assuming photo will be stored as a file or URL

  /*useEffect(() => {
    // Fetch areas from backend when component mounts
    fetchAreas();
  }, []);

  const fetchAreas = async () => {
    try {
      const response = await axios.get('your_backend_api_url/areas');
      setAreas(response.data); // Assuming response.data is an array of area objects
    } catch (error) {
      console.error('Error fetching areas:', error);
    }
  };
*/
  /*const handleChoosePhoto = () => {
    const options = {
      title: 'Select Image',
      storageOptions: {
        skipBackup: true,
        path: 'images',
      },
    };

    ImagePicker.showImagePicker(options, response => {
      if (response.didCancel) {
        console.log('User cancelled image picker');
      } else if (response.error) {
        console.log('ImagePicker Error: ', response.error);
      } else {
        const source = { uri: response.uri };
        setPhoto(source);
      }
    });
  };*/


  const handleSubmit = () => {
    // Save form data to backend
    const formData = {
      area: area,
      address: address,
      title: title,
      description: description,
      photo: photo
    };
    // Send formData to backend using axios or fetch API
    console.log(formData);
    // Reset form fields after submission
    setArea('');
    setAddress('');
    setTitle('');
    setDescription('');
    setPhoto(null);
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
          <Picker.Item key={area.id} label={area.name} value={area.id} />
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
      {/*{photo && <Image source={photo} style={{ width: 200, height: 200 }} />}
      <Button title="Choose Photo" onPress={handleChoosePhoto} />*/}
      
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
    marginBottom: 20, // Increase marginBottom to match the provided styling
    borderWidth: 1,
    borderColor: '#40E0D0', // Change borderColor to the provided color
    borderRadius: 6, // Apply borderRadius as provided
  },
  input: {
    width: '100%',
    padding: 10,
    marginBottom: 20, // Increase marginBottom to match the provided styling
    borderWidth: 1,
    borderColor: '#40E0D0', // Change borderColor to the provided color
    borderRadius: 6, // Apply borderRadius as provided
    fontSize: 18, // Apply fontSize as provided
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
