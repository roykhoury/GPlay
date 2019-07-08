import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { Button } from 'react-native-elements';
import Room from './room';

export default class MainMenu extends React.Component {
    // Init page into loading mode
    state = {
        room: null,
        loadingJoinRoom: false,
        loadingCreateRoom: false,
        navigation: {
            loadMainMenu: true,
            loadRoomCreated: false,
            loadRoomJoined: false,
        }
    };

    onPressCreateRoom = async() => {
        this.setState({ loadingCreateRoom: true });
        let createRoomUrl = 'http://192.168.0.107:8080/room/create';
        fetch(createRoomUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic cm95a2hvdXJ5OmFkbWluUG93ZXI=',
            },
            body: "Some title"
        }).then((res) => res.json())
            .then(resJson => {
                this.setState({
                    loadingCreateRoom: false,
                    room: resJson,
                    navigation: {
                        loadMainMenu: false,
                        loadRoomJoined: false,
                        loadRoomCreated: true,
                    }
                })
            })
            .catch((error) => {
                this.setState({ loadingCreateRoom: false });
                console.log(error)
            });
    }

    onPressJoinRoom = async() => {
        this.setState({ loadingJoinRoom: true });
        let joinRoomUrl = 'http://192.168.0.107:8080/room/join/93';
        fetch(joinRoomUrl, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic cm95a2hvdXJ5OmFkbWluUG93ZXI=',
            }
        }).then((res) => res.json())
        .then(resJson => {
            this.setState({
                loadingJoinRoom: false,
                room: resJson,
                navigation: {
                    loadMainMenu: false,
                    loadRoomJoined: true,
                    loadRoomCreated: false,
                }
            })
        })
        .catch((error) => {
            console.log(error);
            this.setState({ loadingJoinRoom: false });
        });
    }

    render() {
        return (
            <View style={styles.container}>
                {this.state.navigation.loadMainMenu ?
                    <Text style={styles.welcomeText}>Welcome back, {this.props.hostUser.username}!</Text>
                : null}
                {this.state.navigation.loadMainMenu ?
                    <Button
                        title="Create room"
                        onPress={this.onPressCreateRoom}
                        containerStyle={styles.menuButton}
                        loading={this.state.loadingCreateRoom}
                    />
                : null}
                {this.state.navigation.loadMainMenu ?
                    <Button
                        title="Join room"
                        onPress={this.onPressJoinRoom}
                        containerStyle={styles.menuButton}
                        loading={this.state.loadingJoinRoom}
                    />
                : null}
                {this.state.navigation.loadRoomCreated || this.state.navigation.loadRoomJoined ? 
                    <Room room={this.state.room} updateHead={this.state.navigation.loadRoomJoined} />
                : null}
            </View>
        );
    }
  
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'rgb(4,4,4)',
        justifyContent: 'center',
    },
    menuButton: {
        width: 200,
        alignSelf: 'center',
        marginBottom: 5,
    },
    welcomeText: {
        color: 'white',
    }
});