import React, { Component } from 'react'
import axios from 'axios'

class Reader extends Component
{
    constructor(props){
        super(props)

        this.state = {
            connectionTypes: [],
            connectionType:'',
            connections: [],
            connectionName: '',
            databases: [],
            database: '',
            tables: [],
            table: '',
            collection:'',
            connectionConfigId:''
        }
    }

    componentDidMount() {
        axios.get('http://localhost:9001/mdep-backend/api/connections/v1/getAllConnectionTypes')
        .then(response => {
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
        }, () => {
            axios.get('http://localhost:9001/mdep-backend/api/connections/v1/getConnectionByConnectionType/connectionType='+this.state.connectionType)
            .then(response => {
                this.setState({
                    connections: response.data.connectionConfig
                })
            })
            .catch(error => {
                console.log(error);
            })
        })
    }

    onConnectionNameChange = event => {
        this.setState({
            connectionName: event.target.value
        }, () => {
            let selectedCollection = this.state.connections.find((element) => {
                return element.connectionName === this.state.connectionName
            })
            this.setState({
                connectionConfigId: selectedCollection.connectionConfigId
            })
            const URL = 'http://localhost:9001/mdep-backend/api/connector/v1/getDatabaseList/connectionConfigurationId='+selectedCollection.connectionConfigId+'/connectionType='+this.state.connectionType
            axios.get(URL)
            .then(response => {
                console.log(response.data);
                this.setState({
                    databases: response.data
                })
            })
            .catch(error => {
                console.log(error);
            })
        })
    }

    onDatabaseChange = event => {
        this.setState({
            database: event.target.value
        }, () => {
            const URL = 'http://localhost:9001/mdep-backend/api/connector/v1/getTableList/connectionConfigurationId='+this.state.connectionConfigId+'/connectionType='+this.state.connectionType+'/databaseName='+this.state.database
            axios.get(URL)
            .then(response => {
                console.log(response.data);
                this.setState({
                    tables: response.data
                })
            })
            .catch(error => {
                console.log(error);
            })
        })
    }

    onTableChange = event => {
        this.setState({
            table: event.target.value
        }, () => {
            console.log('this.state.table - '+this.state.table);
        })
    }

    onSaveClick = event => {
        event.preventDefault();
    }

    render()
    {
        const { connectionTypes, connections, databases, tables, table } = this.state
        let configuration;
        let connectionContent;
        let databasesContent;
        let tablesContent;
        let buttonContent;
        if(connections && connections.length)
        {
            connectionContent = <div>
                    <select value={this.state.connectionName} onChange={this.onConnectionNameChange}>
                        <option>Select Connection</option>
                        {
                            connections.length 
                            ?  connections.map(connection => <option key={connection.connectionName}>{connection.connectionName}</option>) 
                            : null                        
                        }
                    </select>
                </div>
        }
        if(databases && databases.length)
        {
            databasesContent = <div>
                <select value={this.state.database} onChange={this.onDatabaseChange}>
                    <option>Select Database</option>
                    {
                        
                        databases.length 
                        ?  databases.map(database => <option key={database}>{database}</option>) 
                        : null                        
                    }
                </select>
            </div>
        }
        if(tables && tables.length)
        {
            tablesContent = <div>
                <select value={this.state.table} onChange={this.onTableChange}>
                    <option>Select</option>
                    {
                        tables.length 
                        ?  tables.map(table => <option key={table}>{table}</option>) 
                        : null                        
                    }
                </select>
            </div>
        }
        if(table)
        {
            buttonContent = <div>
                <button onClick={this.onSaveClick}>Save</button>
            </div>
        }
        if(this.state.connectionType === 'MYSQL')
        {
            configuration = <div>
                <form>
                    {connectionContent}
                    {databasesContent}    
                    {tablesContent}
                    {buttonContent}
                </form>
            </div>
        }
        else if(this.state.connectionType === 'MONGODB')
        {
            configuration = <div>
                <select value={this.state.connectionName} onChange={this.onConnectionNameChange}>
                    <option>Select Connection</option>
                    {
                        
                        connections.length 
                        ?  connections.map(connection => <option key={connection.connectionName}>{connection.connectionName}</option>) 
                        : null                        
                    }
                    </select>
            </div>
        }
        return(
            <div>
                 <h1>Reader Configuration</h1>
                 
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

export default Reader