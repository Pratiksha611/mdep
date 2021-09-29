import React, { Component } from 'react'
import Reader from './Reader'
import Writer from './Writer'

class Jobs extends Component
{
    onSaveClick = () => {

    }

    render(){
        return(
            <div>
                <h1>Welcome to Jobs Manager</h1>
                <Reader />
                <Writer />
                <br></br><br></br>
                <button onClick={this.onSaveClick}>Execute</button>
            </div>
        )
    }
}

export default Jobs