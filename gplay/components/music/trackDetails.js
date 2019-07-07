import React, { Component } from 'react';
import { Ionicons } from '@expo/vector-icons';

import {
  View,
  KeyboardAvoidingView,
  Text,
  StyleSheet,
  Image,
  TouchableHighlight,
  TouchableOpacity,
  Dimensions,
} from 'react-native';

const TrackDetails = ({
  title,
  artist,
  onAddPress,
  onMorePress,
  onTitlePress,
  onArtistPress,
}) => (
    <View style={styles.container}>
      <TouchableOpacity onPress={onAddPress}>
        <Ionicons name="md-add" size={24} color="white" />
      </TouchableOpacity>
      <View style={styles.detailsWrapper}>
        <Text style={styles.title} onPress={onTitlePress}>{title}</Text>
        <Text style={styles.artist} onPress={onArtistPress}>{artist}</Text>
      </View>
      <TouchableOpacity onPress={onMorePress}>
        <View style={styles.moreButton}>
          <Ionicons name="md-more" size={24} color="white" />
        </View>
      </TouchableOpacity>
    </View>
  );

export default TrackDetails;

const styles = StyleSheet.create({
  container: {
    paddingTop: 44,
    flexDirection: 'row',
    paddingLeft: 20,
    alignItems: 'center',
    paddingRight: 20,
  },
  detailsWrapper: {
    justifyContent: 'center',
    alignItems: 'center',
    flex: 1,
  },
  title: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'white',
    textAlign: 'center',
  },
  artist: {
    color: 'rgba(255, 255, 255, 0.72)',
    fontSize: 12,
    marginTop: 4,
  },
  button: {
    opacity: 0.72,
  },
  moreButton: {
    opacity: 0.72,
    width: 20,
    height: 20,
    alignItems: 'center',
    justifyContent: 'center',
  },
  moreButtonIcon: {
    height: 17,
    width: 17,
  }
});
