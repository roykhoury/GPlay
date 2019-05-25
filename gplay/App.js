import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import TrackDetails from './components/trackDetails';
import Controls from './components/controls';
import SeekBar from './components/seekbar';
import { Ionicons } from '@expo/vector-icons';
import { AppLoading, Font } from 'expo';

export default class App extends React.Component {
  // Init page into loading mode
  state = {
      isLoadingComplete: false,
  };

  // Display the page content
  _handleFinishLoading = () => {
    this.setState({ isLoadingComplete: true });
  };

  // Load the ionIcons before displaying the page content
  _loadResourcesAsync = async() => {
    return Promise.all([
      Font.loadAsync({
        ...Ionicons.font,
      })
    ]);
  }

  _handleLoadingError = error => {
    console.error(error);
  }

  render() {
    if (!this.state.isLoadingComplete) {
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
          <SeekBar trackLength={204} currentPosition={156} />
          <Controls />
        </View>
      );
    }
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'rgb(4,4,4)',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
