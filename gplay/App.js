import React from 'react';
import { StyleSheet, View } from 'react-native';
import { ThemeProvider, Button } from 'react-native-elements';
import MainMenu from './views/mainMenu';

export default class App extends React.Component {
    // Init page into loading mode
    state = {
        loading: false,
        loggedInUser: {},
        navigation: {
            loadMainMenu: true,
            loadHome: false,
        }
    };

    login = async () => {
        this.setState({ loading: true });
        let loginUrl = 'http://192.168.0.108:8080/user/login';
        fetch(loginUrl, {
            method: 'POST',
            headers: {
                Accept: '*/*',
                'Content-Type': 'application/json',
                'Authorization': 'Basic cm95a2hvdXJ5OmFkbWluUG93ZXI=',
            },
            body: JSON.stringify({
                "username": "someNewUser2",
                "password": "somePassword2",
            })
        }).then((res) => res.json())
            .then(resJson => {
                this.setState({
                    loading: false,
                    loggedInUser: resJson,
                    navigation: {
                        loadMainMenu: false,
                        loadHome: true,
                    }
                });
            })
            .catch((error) => {
                this.setState({ loading: false });
                console.log(error)
            });
    }

    render() {
        return (
            <ThemeProvider theme={theme}>
                <View style={styles.container} behavior="padding" enabled>
                    {this.state.navigation.loadMainMenu ?
                        <Button
                            title="Log in"
                            onPress={this.login}
                            containerStyle={styles.loginButton}
                            loading={this.state.loading}
                        />
                    : null}
                    {this.state.navigation.loadHome ?
                        <MainMenu hostUser={this.state.loggedInUser} />
                    : null}
                </View>
            </ThemeProvider>
        );
    }
}
const theme = {
    colors: {
        primary: '#389E9E',
    },
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'rgb(4,4,4)',
        justifyContent: 'center',
    },
    loginButton: {
        width: 200,
        alignSelf: 'center',
    }
});
