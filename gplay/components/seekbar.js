import React from 'react';
import {StyleSheet, Text, View, Slider} from 'react-native';

export default class SeekBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      value: 50,
    };
  }

  change(value) {
    this.setState(() => {
      return {
        value: parseFloat(value),
      };
    });
  }

  render() {
    const {value} = this.state;
    return (
      <View style={styles.container}>
        <Slider
          style={styles.slider}
          step={1}
          maximumValue={200}
          onValueChange={this.change.bind(this)}
          value={value}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    paddingLeft: 16,
    paddingRight: 16,
    paddingTop: 16,
  },
  slider: {
    width: 400,
    marginTop: -12,
  },
});
