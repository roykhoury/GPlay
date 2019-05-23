import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import Header from './components/header';
import AlbumArt from './components/albumArt';
import TrackDetails from './components/trackDetails';
import Controls from './components/controls';
import SeekBar from './components/seekbar';

export default class App extends React.Component {
  state = {
      loading: true
  };

  componentDidMount() {
    this.setState({ loading: false });
  }

  render() {
    const { loading } = this.state;

    if(loading) {
      return (
        <View style={styles.container}>
          <Text style={{color: 'white'}}>
            Loading
          </Text>
        </View>
      );
    }

    return (
      <View style={styles.container}>
        <Header message="Some Header Te" />
        <AlbumArt url="./assets/icon.png" />
        <TrackDetails title="Some song title" artist="Some artist" />
        <SeekBar trackLength={204} currentPosition={156} />
        <Controls />
      </View>
    );
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
