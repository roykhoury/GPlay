import React from 'react';
import { Avatar } from 'react-native-elements';
import { Text } from 'react-native';

import {
  View,
  StyleSheet,
  FlatList,
} from 'react-native';

export default class ChatHeads extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
        };
    }

    updateHeads = async() => {
        let getUsersUrl = 'http://192.168.0.108:8080/user/getUsers?userIds=' + this.props.members;
        fetch(getUsersUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic cm95a2hvdXJ5OmFkbWluUG93ZXI=',
            }
        }).then((res) => res.json())
        .then(resJson => {
            this.setState(state => {
                users: [...state.users, resJson]
            });
        })
        .catch((error) => {
            console.log(error);
        });
    }

    // Initial messages in the group chat
    componentWillMount() {
        this.updateHeads();
        this.setState({
            users: [
                {
                    _id: this.props.host.id,
                    initials: 'SS',
                    name: this.props.host.username,
                    avatar: 'https://s3.amazonaws.com/uifaces/faces/twitter/ladylexy/128.jpg',
                },
            ],
        })
    }

    renderItem = user => {
        let red = Math.floor(Math.random() * 255);
        let blue = Math.floor(Math.random() * 255);
        let green = Math.floor(Math.random() * 255);

        return (
            <Avatar
                rounded
                title={user.item.initials}
                size='medium'
                source={{uri: user.item.avatar}}
                titleStyle={{color: '#eee', fontSize: 15}}
                containerStyle={styles.head}
                overlayContainerStyle={{backgroundColor: 'rgba('+red+','+blue+','+green+', 0.7)'}}
            />
        )
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={{color: 'white'}}>{this.state.users.size}</Text>
                <FlatList
                    horizontal={true}
                    data={this.state.users}
                    keyExtractor={(item, index) => item._id.toString()}
                    renderItem={this.renderItem}
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        height: 72,
        paddingTop: 8,
        flexDirection: 'row',
        borderTopWidth: 1,
        borderTopColor: '#111',
        borderBottomWidth: 1,
        borderBottomColor: '#111',
    },
    head: {
        marginLeft: 15,
    }
});
