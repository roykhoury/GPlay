import React from 'react';
import { Ionicons } from '@expo/vector-icons';

import {
  View,
  StyleSheet,
  TouchableOpacity,
} from 'react-native';

const Controls = ({
  paused,
  shuffleOn,
  repeatOn,
  onPressPlay,
  onPressPause,
  onBack,
  onForward,
  onPressShuffle,
  onPressRepeat,
  forwardDisabled,
}) => (
  <View style={styles.container}>

    <TouchableOpacity activeOpacity={0.0} onPress={onPressShuffle}>
        <Ionicons name="md-shuffle" size={30} color="white" />
    </TouchableOpacity>

    <View style={{width: 40}} />
      <TouchableOpacity onPress={onBack}>
        <Ionicons name="md-skip-backward" size={30} color="white" />
      </TouchableOpacity>
    <View style={{width: 20}} />

    {!paused ?
      <TouchableOpacity onPress={onPressPause}>
        <View style={styles.pauseButton}>
          <Ionicons name="md-pause" size={30} color="white" />
        </View>
      </TouchableOpacity> :
      <TouchableOpacity onPress={onPressPlay}>
        <View style={styles.playButton}>
          <Ionicons name="md-play" size={30} color="white" />
        </View>
      </TouchableOpacity>
    }
  
    <View style={{width: 20}} />
    <TouchableOpacity onPress={onForward} disabled={forwardDisabled}>
        <Ionicons style={[forwardDisabled && {opacity: 0.3}]}
          name="md-skip-forward" size={30} color="white" />
    </TouchableOpacity>

    <View style={{width: 40}} />
    <TouchableOpacity activeOpacity={0.0} onPress={onPressRepeat}>
      <Ionicons name="md-repeat" size={30} color="white" />
    </TouchableOpacity>

  </View>
);

export default Controls;

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: 5,
    paddingBottom: 10,
  },
  playButton: {
    height: 60,
    width: 60,
    borderWidth: 1,
    borderColor: 'white',
    borderRadius: 65 / 2,
    alignItems: 'center',
    justifyContent: 'center',
    paddingLeft: 5,
  },
  pauseButton: {
    height: 60,
    width: 60,
    borderWidth: 1,
    borderColor: 'white',
    borderRadius: 65 / 2,
    alignItems: 'center',
    justifyContent: 'center',
    paddingLeft: 5,
    paddingRight: 5,
  },
})
