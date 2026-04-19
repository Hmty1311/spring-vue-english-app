import axios from 'axios'
import { axiosClient } from './axiosClient'

export const login = async () => {
    try{
        const response = await axiosClient.post('/api/login', {
            userName: 'user',
            password: '1234'
        })

        console.log('response:', response.data)
    }catch(error){
        console.error('API error', error)
    }
}