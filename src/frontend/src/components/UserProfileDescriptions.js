import React, { useEffect, useState } from 'react';
import { Descriptions } from 'antd';
import { getUserInformation } from '../clients/UserClient';

function UserProfileDescriptions() {
    const [userInfo, setUserInfo] = useState(null);

    useEffect(() => {
        const jwtToken = localStorage.getItem('jwt');
        const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));

        getUserInformation(decodedToken.UserId)
            .then((res) => res.json())
            .then((data) => {
                setUserInfo(data);
            });
    }, []);

    return (
        <div className="user-profile-descriptions">
            {userInfo && (
                <div>
                    <Descriptions
                        title="User Info"
                        bordered
                        column={1}
                        className="custom-descriptions"
                    >
                        <Descriptions.Item label="Username">{userInfo.username}</Descriptions.Item>
                        <Descriptions.Item label="Email">{userInfo.email}</Descriptions.Item>
                        <Descriptions.Item label="Age">{userInfo.age}</Descriptions.Item>
                        <Descriptions.Item label="Height">{userInfo.height}</Descriptions.Item>
                        <Descriptions.Item label="Gender">{userInfo.gender}</Descriptions.Item>
                        <Descriptions.Item label="Activity level">{userInfo.activityLevel}</Descriptions.Item>
                    </Descriptions>
                    <Descriptions
                        title="Diet Info"
                        bordered
                        column={1}
                        className="custom-descriptions"
                    >
                        <Descriptions.Item label="Diet Name">{userInfo.diet.name}</Descriptions.Item>
                        <Descriptions.Item label="Calories demand">{userInfo.caloricDemand}</Descriptions.Item>
                        <Descriptions.Item label="Protein">{userInfo.diet.protein}</Descriptions.Item>
                        <Descriptions.Item label="Fat">{userInfo.diet.fat}</Descriptions.Item>
                        <Descriptions.Item label="Carbs">{userInfo.diet.carbs}</Descriptions.Item>
                    </Descriptions>
                </div>
            )}
        </div>
    );
}

export default UserProfileDescriptions;
