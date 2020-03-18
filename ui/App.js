import { Audio } from 'expo-av';
import React, { Component } from 'react';
import { Button, StyleSheet, View, Text } from 'react-native';
import { Table, TableWrapper, Row, Rows, Col, Cols, Cell } from 'react-native-table-component';

export default class ButtonBasics extends Component {

    state = {
        error: null,
        isPlaying: false,
        soundObject: new Audio.Sound(),
        audioUrl: 'http://192.168.0.108:8080/music/play?id=5d82f69b8d76f84d9c47176f'
    };

    _login = async () => {
        let loginUrl = 'http://192.168.0.108:8080/user/login';
        fetch(loginUrl, {
            method: 'POST',
            headers: {
                Accept: '*/*',
                'Content-Type': 'application/json',
                'Authorization': 'Basic cm95a2hvdXJ5OmFkbWluUG93ZXI=',
            },
            body: JSON.stringify({
                "username": "someNewUser",
                "password": "somePassword",
            })
        }).then((res) => res.json())
            .then(resJson => {
                // logged in now
            })
            .catch((error) => {
                alert('Uh oh ... ' + error);
                this.setState({
                    error: error,
                })
            });
    };

    _onPlay = async () => {
        this.setState({
            isPlaying: !this.state.isPlaying
        });

        try {
            if (this.state.isPlaying) {
                this.state.soundObject.pauseAsync();
            } else {
                this.state.soundObject.playAsync();
            }
          } catch (error) {
            console.log(error);
          }
    };

    componentDidMount() {
        // Make a request to check if this room has already a song playing or not... (make a new endpoit?)
        // If it does, load the song, and after the song has loaded, play the song from that point
        // We preload the song in this method
        this._login();
        if (!this.state.soundObject._loaded) {
            this.state.soundObject.loadAsync({
              uri: this.state.audioUrl,
              headers: {
                Accept: '*/*',
                'Content-Type': 'application/json',
                'Authorization': 'Basic cm95a2hvdXJ5OmFkbWluUG93ZXI=',
              }
            })
            .then( res => {
                console.log(this.state.soundObject._loaded);
            })
          }
    }

    render() {
        return (
            <View style={styles.container}>
                <View>
                    <Text>{this.state.error}</Text>
                    <Button
                        onPress={this._onPlay}
                        title={this.state.isPlaying ? 'Pause' : 'Play'}
                    />
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
    }
});
