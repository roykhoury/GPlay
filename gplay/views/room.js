import React from 'react';
import { StyleSheet, View } from 'react-native';
import TrackDetails from '../components/music/trackDetails';
import Controls from '../components/music/controls';
import SeekBar from '../components/music/seekbar';
import { Ionicons } from '@expo/vector-icons';
import { AppLoading, Font, Audio } from 'expo';
import ChatMessages from '../components/chat/chatMessages';
import ChatHeads from '../components/chat/chatHeads';

export default class Room extends React.Component {
  // Init page into loading mode
  state = {
    paused: true,
    isLoadingComplete: false,
    isRenderingComplete: false,
    soundObject: new Audio.Sound(),
    audioUrl: 'http://192.168.0.106:8080/music/play?id=5cd50d3256548b22c422c2b4',
  };

  // Display the page content
  _handleFinishLoading = () => {
    this.setState({ isRenderingComplete: true });
  };

  // Load the ionIcons before displaying the page content
  _loadResourcesAsync = async () => {
    return Promise.all([
      Font.loadAsync({
        ...Ionicons.font,
      })
    ]);
  }

  _handleLoadingError = error => {
    console.error(error);
  }

  // Play song from audioUrl
  onPressPlay = async () => {
    try {
      if (!this.state.soundObject._loaded) {
        await this.state.soundObject.loadAsync({
          uri: this.state.audioUrl,
          headers: {
            Accept: '*/*',
            'Content-Type': 'application/json',
            'Authorization': 'Basic cm95a2hvdXJ5OmFkbWluUG93ZXI=',
          }
        });
      }
      await this.state.soundObject.playAsync();
      await this.setState({ paused: false });
    } catch (error) {
      console.log(error);
    }
  }

  onPressPause = () => {
    this.setState({ paused: true });
    this.state.soundObject.pauseAsync();
  }

  render() {
    if (!this.state.isRenderingComplete && !this.state.isLoadingComplete) {
      return (
        <AppLoading
          startAsync={this._loadResourcesAsync}
          onError={this._handleLoadingError}
          onFinish={this._handleFinishLoading}
        />
      );
    } else {
      return (
        <View style={styles.container}>
          <TrackDetails title="Some song title" artist="Some artist" />
          <SeekBar />
          <Controls
            onPressPlay={this.onPressPlay}
            onPressPause={this.onPressPause}
            paused={this.state.paused}
          />
          <ChatHeads host={this.props.room.host} members={this.props.room.memberIds} updateHead={this.props.updateHead} />
          <ChatMessages />
        </View>
      );
    }
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'rgb(4,4,4)',
    justifyContent: 'center',
  }
});
