import React, {useEffect, useState} from 'react';
import { Descriptions } from 'antd';
import {getUserInformation} from "../clients/UserClient";
import {compile} from "jsonpointer";


function UserProfileDescriptions() {
    const [userId, setUserId] = useState(null)
    const [userInfo, setUserInfo] = useState(null);

    useEffect(() => {
        const jwtToken = localStorage.getItem("jwt");
        console.log(jwtToken)
        const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
        setUserId(decodedToken.UserId);

        getUserInformation(decodedToken.UserId)
            .then(res => res.json())
            .then(data => {
                setUserInfo(data)
                console.log(data)
            })

    }, []);
    return (
        <div className="user-profile-descriptions">
            <Descriptions title="User Info">
                {userInfo && (
                <>
                    <Descriptions.Item label="Username">{userInfo.username}</Descriptions.Item>
                    <Descriptions.Item label="Email">{userInfo.email}</Descriptions.Item>
                    <Descriptions.Item label="Age">{userInfo.age}</Descriptions.Item>
                    <Descriptions.Item label="Height">{userInfo.height}</Descriptions.Item>
                    <Descriptions.Item label="Gender">{userInfo.gender}</Descriptions.Item>
                    <Descriptions.Item label="Activity level">{userInfo.activityLevel}</Descriptions.Item>
                    <Descriptions.Item label="Calories demand">{userInfo.caloricDemand}</Descriptions.Item>
                </>
                    )}
            </Descriptions>
        </div>
    );
}
export default UserProfileDescriptions;