import React, { Component } from 'react'
import axios from 'axios'

class ConnectionTypes extends Component
{
    constructor(props) {
        super(props)

        this.state = {
            connectionTypes: []
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

    render()
    {
        const { connectionTypes } = this.state
        console.log(connectionTypes);
        return(
            <div>
                <select>
                <option>Select Connection Type</option>
                 {
                     
                     connectionTypes.length 
                     ?  connectionTypes.map(connectionType => <option>{connectionType}</option>) 
                     : null
                     
                 }
                 </select>
            </div>
        )
    }
}

export default ConnectionTypes