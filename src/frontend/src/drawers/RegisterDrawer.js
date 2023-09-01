import {Button, Col, Drawer, Form, Input, InputNumber, Row, Select, Space} from "antd";
import {useState} from "react";
import {registerUser} from "../clients/RegisterClinet";


const RegisterDrawer = ({visible, close}) => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [age, setAge] = useState("");
    const [weight, setWeight] = useState("");
    const [height, setHeight] = useState("");
    const [gender, setGender] = useState("");
    const [activityLever, setActivityLever] = useState("");

    function sendRegisterRequest () {
        const registerBody = {
            username: username,
            email: email,
            password: password,
            age: age,
            weight: weight,
            height: height,
            gender: gender,
            activityLevel: activityLever
        };
        console.log(registerBody)
        registerUser(registerBody)
            .then(() => {
                console.log(registerBody)
            }).catch(error => {
                console.log(error);
        })
    }

    return (
        <>
            <Drawer
                title="Create a new account"
                width={720}
                onClose={close}
                open={visible}
                bodyStyle={{paddingBottom: 80}}
                extra={
                    <Space>
                        <Button onClick={close}>Cancel</Button>
                        <Button onClick={() => sendRegisterRequest()} type="primary">
                            Submit
                        </Button>
                    </Space>
                }
            >
                <Form layout="vertical" hideRequiredMark>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                name="username"
                                label="Username"
                                rules={[{ required: true, message: 'Please enter username' }]}
                            >
                                <Input value={username} placeholder="Please enter username" onChange={(e) => setUsername(e.target.value)} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                name="email"
                                label="Email"
                                rules={[{ required: true, message: 'Please enter email' }]}
                            >
                                <Input value={email} placeholder="Please enter email" onChange={(e) => setEmail(e.target.value)} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                name="password"
                                label="Password"
                                rules={[{ required: true, message: 'Please enter password' }]}
                            >
                                <Input.Password value={password} placeholder="Please enter password" onChange={(e) => setPassword(e.target.value)} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                name="age"
                                label="Age"
                                rules={[{ required: true, message: 'Please enter age' }]}
                            >
                                <Input value={age} placeholder="Please enter age" onChange={(value) => setAge(value.target.value)} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                name="weight"
                                label="Weight"
                                rules={[{ required: true, message: 'Please enter weight' }]}
                            >
                                <Input value={weight} placeholder="Please enter weight" onChange={(value) => setWeight(value.target.value)} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                name="height"
                                label="Height"
                                rules={[{ required: true, message: 'Please enter height' }]}
                            >
                                <Input value={height} placeholder="Please enter height" onChange={(value) => setHeight(value.target.value)} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                name="gender"
                                label="Gender"
                                rules={[{ required: true, message: 'Please select gender' }]}
                            >
                                <Select value={gender} placeholder="Please select gender" onChange={(value) => setGender(value)}>
                                    <Select.Option value="MALE">Male</Select.Option>
                                    <Select.Option value="FEMALE">Female</Select.Option>
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                name="activityLevel"
                                label="Activity Level"
                                rules={[{ required: true, message: 'Please select activity level' }]}
                            >
                                <Select value={activityLever} placeholder="Please select activity level" onChange={(value) => setActivityLever(value)}>
                                    <Select.Option value="1">Sedentary</Select.Option>
                                    <Select.Option value="2">Lightly active</Select.Option>
                                    <Select.Option value="3">Moderately active</Select.Option>
                                    <Select.Option value="4">Very active</Select.Option>
                                    <Select.Option value="5">Super active</Select.Option>

                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </Drawer>
        </>
    );
};

export default RegisterDrawer;
