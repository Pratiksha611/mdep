import React, { Component } from 'react'
import Welcome from './Welcome'
import Connections from './Connections'
import Jobs from './Jobs'

class Home extends Component
{
    constructor(props){
        super(props)

        this.state = {
            page: 'Home'
        }
    }

    onDropDownChange = (event) => {
        this.setState({
            page: event.target.value
          })
    }

    render()
    {
        let content
        if(this.state.page === 'Home')
        {
            content = <div>
            <Welcome/>
            </div>
        }
        else if(this.state.page === 'Connections')
        {
            content = <div>
            <Connections/>
            </div>
        }
        else if(this.state.page === 'Jobs')
        {
            content = <div>
            <Jobs/>
            </div>
        }
        else{
            content = <div>
            <Welcome/>
            </div>
        }

        return(
            <div>
                <select value={this.state.page} onChange={this.onDropDownChange}>
                    <option key="Home">Home</option>
                    <option key="Connections">Connections</option>
                    <option key="Jobs">Jobs</option>
                </select>
                <div>{content}</div>
            </div>
        )
    }
}

export default Home