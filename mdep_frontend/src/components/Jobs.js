import React, { Component } from 'react'
import Reader from './Reader'
import Writer from './Writer'

class Jobs extends Component
{
    render(){
        return(
            <div>
                <h1>Welcome to Jobs Manager</h1>
                <Reader />
                <Writer />
            </div>
        )
    }
}

export default Jobs