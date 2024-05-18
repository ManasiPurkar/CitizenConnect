import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

export default function header(props){
    return (
        <View style={styles.header}>
            <Text style={styles.title}> {props.text} </Text>
        </View>
    )
}

const styles = StyleSheet.create({
    header:{
        height: 80,
        paddingTop: 30,
        backgroundColor: '#40E0D0'
    },
    title:{
        textAlign: 'center',
        fontSize: 20,
        fontWeight: 'bold'
    }
});