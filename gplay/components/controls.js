import React, { Component } from 'react';
import { Ionicons } from '@expo/vector-icons';

import {
  View,
  Text,
  StyleSheet,
  Image,
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
        <Ionicons name="md-shuffle" size={32} color="white" />
    </TouchableOpacity>

    <View style={{width: 40}} />
    <TouchableOpacity onPress={onBack}>
      <Ionicons name="md-skip-backward" size={32} color="white" />
    </TouchableOpacity>
    <View style={{width: 20}} />

    {!paused ?
      <TouchableOpacity onPress={onPressPause}>
        <View style={styles.playButton}>
          <Ionicons name="md-play" size={32} color="white" />
        </View>
      </TouchableOpacity> :
      <TouchableOpacity onPress={onPressPlay}>
        <View style={styles.playButton}>
          <Ionicons name="md-pause" size={32} color="white" />
        </View>
      </TouchableOpacity>
    }

    <View style={{width: 20}} />
    <TouchableOpacity onPress={onForward}
      disabled={forwardDisabled}>
        <Ionicons style={[forwardDisabled && {opacity: 0.3}]}
          name="md-skip-forward" size={32} color="white" />
    </TouchableOpacity>

    <View style={{width: 40}} />
    <TouchableOpacity activeOpacity={0.0} onPress={onPressRepeat}>
      <Ionicons name="md-repeat" size={32} color="white" />
    </TouchableOpacity>

  </View>
);

export default Controls;

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: 15,
  },
  playButton: {
    height: 65,
    width: 65,
    borderWidth: 1,
    borderColor: 'white',
    borderRadius: 65 / 2,
    alignItems: 'center',
    justifyContent: 'center',
    paddingLeft: 5,
    marginRight: 5,
  },
  secondaryControl: {
    height: 18,
    width: 18,
  },
  off: {
    opacity: 0.30,
  }
})
