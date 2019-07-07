import React, { Component } from 'react';
import { StyleSheet, View } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { GiftedChat, Bubble, Send, InputToolbar } from 'react-native-gifted-chat';

export default class ChatMessages extends Component {  
    state = {
        messages: [],
    }

    // Initial messages in the group chat
    componentWillMount() {
        this.setState({
            messages: [
            {
                _id: 1,
                text: 'Hello developer',
                createdAt: new Date(),
                user: {
                    _id: 2,
                    initials: 'RN',
                    name: 'React Native',
                    avatar: 'https://placeimg.com/140/140/any',
                },
            },
            ],
        })
    }
    
    // On sending the message
    onSend(messages = []) {
        this.setState(previousState => ({
            messages: GiftedChat.append(previousState.messages, messages),
        }))
    }

    // Custom bubble for chat
    renderBubble(props) {
        return (
            <Bubble
                {...props}
                textStyle={{
                    right:{color: 'white'},
                    left: {color: 'white'}
                }}
                wrapperStyle={{
                    left: {backgroundColor: 'transparent'},
                    right:{backgroundColor: 'transparent'}
                }}
            />
        );
    }

        // The message input box container
    renderInputToolbar(props) {
        return (
            <InputToolbar
                {...props}
                containerStyle={styles.messageInput}
                placeholder="Type your message here..."
            />
        )
    }

    renderSend(props) {
        return (
            <Send {...props}>
                <View style={styles.sendButtonWrapper}>
                    <Ionicons
                        style={styles.sendButton}
                        name="md-send"
                        size={30}
                    />
                </View>
            </Send>
        );
    }

    render() {
        return (
            
                <GiftedChat
                    messages={this.state.messages}
                    onSend={messages => this.onSend(messages)}
                    renderBubble={this.renderBubble}
                    renderInputToolbar={this.renderInputToolbar}
                    renderSend={this.renderSend}
                    showUserAvatar
                    user={{
                        _id: 1,
                        initials: 'RK',
                        name: 'Roi Khoury',
                        avatar: 'https://placeimg.com/140/140/any',
                    }}
                />
        )
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 20,
    paddingLeft: 12,
    paddingRight: 12,
  },
  messageInput: {
    backgroundColor: 'rgb(4,4,4)',
    borderTopColor: '#222',
  },
  sendButtonWrapper: {
    borderWidth: 0,
    paddingBottom: 5,
    paddingRight: 10,
  },
  sendButton: {
      color: '#389E9E',
  },
  message: {
    fontSize: 12,
    color: 'red', 
  }
})