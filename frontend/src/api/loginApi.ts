import axios from 'axios'
import { axiosClient } from './axiosClient'

export const login = async () => {
    try{
        const response = await axiosClient.post('/api/auth/login', {
            email: 'test@example.com',
            password: '1234'
        })

        console.log('response:', response.data)
    }catch(error){
        console.error('API error', error)
    }
}