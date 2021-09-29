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
                "connectionConfiguration": "{\"host\": \""+host+"\",\"port\": \""+port+"\", \"username\": \""+username+"\",\"password\": \""+password+"\"}"
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
            configuration = <div class="table">
                <table class="table text-center">
                    <tr>
                        <td>
                            &nbsp;Connection Name&nbsp; <input type="text" value={this.state.connectionName} onChange={this.onConnectionNameChange}></input><br></br><br></br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            &nbsp; Host Name&nbsp;<input type="text" value={this.state.host} onChange={this.onHostChange}></input><br></br><br></br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            &nbsp; Port Number&nbsp;<input type="text" value={this.state.port} onChange={this.onPortChange}></input><br></br><br></br>
                        </td>
                    </tr> 
                    <tr>
                        <td>
                            &nbsp; Username&nbsp; <input type="text" value={this.state.username} onChange={this.onUsernameChange}></input><br></br><br></br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            &nbsp; Password&nbsp;<input type="password" value={this.state.password} onChange={this.onPasswordChange}></input><br></br><br></br>
                        </td>
                    </tr>
                    <tr>
                        <button type="submit" onClick={this.onTestClick}>Test</button> &nbsp; &nbsp;
                        <button type="submit" onClick={this.onSaveClick}>Save</button>
                    </tr>
                </table>
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