import React, { Component } from 'react'
import ConnectionTypes from './ConnectionTypes'
import axios from 'axios'

class Connections extends Component
{
    constructor(props) {
        super(props)

        this.state = {
            connectionTypes: [],
            connectionType: '',
            connectionName: '',
            host: '',
            port: '',
            username: '',
            password: ''
        }
    }

    componentDidMount() {
        axios.get('http://localhost:9001/mdep-backend/api/connections/v1/getAllConnectionTypes')
        .then(response => {
            console.log(response);
            this.setState({
                connectionTypes: response.data
            })
        })
        .catch(error => {
            console.log(error);
        })
    }

    onConnectionTypeChange = (event) => {
        this.setState({
            connectionType: event.target.value
        })
    }

    onConnectionNameChange = (event) => {
        this.setState({
            connectionName: event.target.value
        })
    }

    onHostChange = event => {
        this.setState({
            host: event.target.value
        })
    }

    onPortChange = event => {
        this.setState({
            port: event.target.value
        })
    }

    onUsernameChange = event => {
        this.setState({
            username: event.target.value
        })
    }

    onPasswordChange = event => {
        this.setState({
            password: event.target.value
        })
    }

    onTestClick = event => {
        const { connectionType, host, port, username, password } = this.state
        console.log(this.state.connectionType);
        console.log(connectionType);
        const body = {
            "host": host,
            "password": password,
            "port": port,
            "username": username
          }
        let url = 'http://localhost:9001/mdep-backend/api/connector/v1/testConnection/connectionType='+connectionType
        axios.post(url, body)
        .then(response => {
            console.log(response);
            console.log(response.data);
            alert(response.data.MESSAGE)
        })
        .catch(error => {
            console.log(error);
            alert(error)
        })
        event.preventDefault();
    }

    onSaveClick = event => {
        const { connectionType, connectionName, host, port, username, password } = this.state
        console.log(this.state.connectionType);
        console.log(connectionType);
        const body = {
            "connectionConfig": [
              {
                "connectionName": connectionName,
                "connectionConfiguration": "{\"host\": "+host+",\"port\": "+port+", \"username\": "+username+",\"password\": "+password+"}"
              }
            ],
            "connectionType": connectionType
          }
        let url = 'http://localhost:9001/mdep-backend/api/connections/v1/createConnections'
        axios.post(url, body)
        .then(response => {
            console.log(response);
            const MESSAGE = response.data.connectionConfig[0].connectionName +" Connection created successfully."
            alert(MESSAGE)
        })
        .catch(error => {
            console.log(error);
            alert(error)
        })
        event.preventDefault();
    }

    render()
    {
        let configuration;
        if(this.state.connectionType === 'MYSQL' || this.state.connectionType === 'MONGODB')
        {
            configuration = <div>
                <input type="text" value={this.state.connectionName} onChange={this.onConnectionNameChange} placeholder="connection name"></input>
                <input type="text" value={this.state.host} onChange={this.onHostChange} placeholder="host"></input>
                <input type="text" value={this.state.port} onChange={this.onPortChange} placeholder="port"></input>
                <input type="text" value={this.state.username} onChange={this.onUsernameChange} placeholder="username"></input>
                <input type="password" value={this.state.password} onChange={this.onPasswordChange} placeholder="password"></input>
                <button type="submit" onClick={this.onTestClick}>Test</button>
                <button type="submit" onClick={this.onSaveClick}>Save</button>
            </div>
        }
        const { connectionTypes } = this.state
        return(
            <div>
                 <h1>Welcome to Connection Manager</h1>
                 <form>
                    <select value={this.state.connectionType} onChange={this.onConnectionTypeChange}>
                    <option>Select Connection Type</option>
                    {
                        
                        connectionTypes.length 
                        ?  connectionTypes.map(connectionType => <option key={connectionType}>{connectionType}</option>) 
                        : null
                        
                    }
                    </select>
                    {configuration}
                 </form>
            </div>
        )
    }
}

export default Connections