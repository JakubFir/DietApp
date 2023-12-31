import React, {useState} from "react";
import {Button, Form, Input} from "antd";
import {useNavigate} from "react-router-dom";
import RegisterDrawer from "../drawers/RegisterDrawer";
import {authenticateUser} from "../clients/AuthenticateUser";
import {errorNotification, successNotification} from "../notifications/Notifications";

const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [showDrawer, setShowDrawer] = useState(false)
    const navigate = useNavigate();

    const handleRegister = () => {
        setShowDrawer(true);
    }
    const close = () => {
        setShowDrawer(false);
    };

    const sendLoginRequest = () => {
        if(!username || !password){
            errorNotification("Provide valid information's")
            return;
        }
        const loginBody = {
            username: username,
            password: password,
        };
        authenticateUser(loginBody)
            .then(response => response.json())
            .then(data => {
                localStorage.setItem('jwt', data.token);
                navigate("/profile")
                successNotification("Welcome " + loginBody.username)

            }).catch(error => {
                errorNotification("provide valid credentials")
        })

    };

    return (
        <>
            <RegisterDrawer
                visible={showDrawer}
                close={close}
            />
            <div style={{
                backgroundColor: 'white',
                minHeight: '30vh',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center'
            }}>
                <Form
                    name="basic"
                    labelCol={{
                        span: 8,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}
                    style={{
                        maxWidth: 600,
                    }}
                    initialValues={{
                        remember: true,
                    }}
                    autoComplete="off"
                >
                    <Form.Item
                        name="username"
                        label="Username"
                        style={{
                            width:300
                        }}
                        rules={[{required: true, message: 'Pleas enter your username!'}]}
                    >
                        <Input value={username} placeholder="Pleas enter your username"
                               onChange={(e) => setUsername(e.target.value)}/>
                    </Form.Item>

                    <Form.Item
                        name="password"
                        label="Password"
                        rules={[{required: true, message: 'Pleas enter your password!'}]}
                    >
                        <Input.Password value={password} placeholder="Please enter password"
                                        onChange={(e) => setPassword(e.target.value)}/>
                    </Form.Item>
                    <Form.Item
                        wrapperCol={{
                            offset: 8,
                            span: 16,
                        }}>
                        <div style={{display: 'flex'}}>
                            <Button
                                onClick={() => sendLoginRequest()}
                                style={{marginRight: '5px'}}
                                type="primary" htmlType="submit">
                                Login
                            </Button>

                            <Button
                                onClick={() => handleRegister()}
                                style={{marginRight: '5px'}}
                                type="primary" htmlType="submit">
                                Register
                            </Button>
                        </div>
                    </Form.Item>
                </Form>
            </div>
        </>
    );
}
export default LoginPage;